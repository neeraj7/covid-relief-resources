package com.covid.relief.init;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.covid.relief.constants.Constants;
import com.covid.relief.dto.QueryHistory;
import com.covid.relief.dto.QueryResultImpl;
import com.covid.relief.entity.CityEntity;
import com.covid.relief.entity.PhoneEntity;
import com.covid.relief.entity.TweetEntity;
import com.covid.relief.open.nlp.SentenceDetectorNLP;
import com.covid.relief.repository.CityRepository;
import com.covid.relief.repository.PhoneRepository;
import com.covid.relief.repository.TweetRepository;
import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.UserMentionEntity;

@Component
public class AppInitializer {

	private static final Logger log = LoggerFactory.getLogger(AppInitializer.class);

	private static Map<String, List<QueryHistory>> cities = new HashMap<>(100);

	private static List<QueryHistory> resources = new ArrayList<>();
	
	@Autowired
	private TweetRepository tweetRepo;

	@Autowired
	private PhoneRepository phoneRepo;

	@Autowired
	private CityRepository cityRepo;

	@Autowired
	private SentenceDetectorNLP sentenceDetectorNLP;

	@Value("${covid.relief.twitter.query}")
	private String appendQuery;

	@PostConstruct
	private void init() {
		log.info("Initialization started.");
		// read file
		try {

			Resource resource = new ClassPathResource("resources.txt");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
			br.lines().forEach(l -> resources.add(new QueryHistory(l.toLowerCase())));

			Resource city = new ClassPathResource("cities.txt");
			
			BufferedReader br2 = new BufferedReader(new InputStreamReader(city.getInputStream()));
			br2.lines().forEach(l -> cities.put(l.toLowerCase(), resources));
			
		} catch (IOException e) {
			log.error("Error happened in reading files.");
		}
		log.info("Initialization finished.");
	}

	public static Map<String, List<QueryHistory>> getCitiesMapping() {
		return cities;
	}
	
	public static List<QueryHistory> getResourcesList() {
		return resources;
	}

	// Added 10 seconds delay
	@Scheduled(initialDelay = 10000, fixedDelay = 10000)
	public void run() {
		long start = Instant.now().toEpochMilli();
		log.info("Querying twitter to fetch tweets started at :: " + Calendar.getInstance().getTime());
		cities.forEach((city, resources) -> resources.forEach(queryHistory -> {

			Query query = new Query(Constants.VERIFIED + city + " " + queryHistory.getResource() + " " + appendQuery);
			query.since(LocalDate.now().minusDays(1).toString());

			// if sinceId is present then add it into the query
			if (queryHistory.getSinceId() != 0l) {
				query.setSinceId(queryHistory.getSinceId());
			}

			QueryResult result = fetchQueryResult(query);

			if (result != null && result.getRateLimitStatus().getRemaining() != 0) {

				// updating the sinceId of the resource
				queryHistory.setSinceId(result.getSinceId());

				filterAndSaveTweetsInDB(result, city, queryHistory);

			}

			if (result.getRateLimitStatus().getRemaining() == 0) {
				try {
					if(result.getRateLimitStatus().getSecondsUntilReset() >= 0 ) {
					// wait for refresh limit to reset
					log.info("waiting till the rate limit resets in {} seconds",
							result.getRateLimitStatus().getSecondsUntilReset());
					Thread.sleep(result.getRateLimitStatus().getSecondsUntilReset() * 1000);
					log.info("Wait is over, and querying starts again.");
					}					
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}));
		long end = Instant.now().toEpochMilli();
		log.info("Quering twitter finished :: " + Calendar.getInstance().getTime());
		log.info("Time taken: {} seconds" + (end - start) / 1000);
	}

	private QueryResult fetchQueryResult(Query query) {
		Twitter twitter = TwitterFactory.getSingleton();

		try {
			return twitter.search(query);
		} catch (TwitterException e) {
			log.error("Exception occured while querying the twitter: {}", query.getQuery());
			QueryResultImpl result = new QueryResultImpl();
//			RateLimitStatusImpl status = new RateLimitStatusImpl(e.getRateLimitStatus());
			result.setRateLimitStatus(e.getRateLimitStatus());
			return result;
		}
	}

	/**
	 * Method to filter tweets based on the phone numbers, resources, and cities.
	 * 
	 * @param city
	 * @param queryHistory
	 */
	public void filterAndSaveTweetsInDB(QueryResult result, String city, QueryHistory queryHistory) {

		// Filter out all the tweets which are not stored in DB
		result.getTweets().stream().filter(t -> !tweetRepo.findByTweetId(t.getId()).isPresent()).forEach(tweet -> {

			String tweetText = tweet.getRetweetedStatus() != null ? tweet.getRetweetedStatus().getText()
					: tweet.getText();

			Iterator<PhoneNumberMatch> phoneNumbers = PhoneNumberUtil.getInstance().findNumbers(tweetText, "IN")
					.iterator();

			// Need to check by phone number in DB
			while (phoneNumbers.hasNext()) {
				long phone = phoneNumbers.next().number().getNationalNumber();

				Optional<PhoneEntity> phoneEntity = phoneRepo.findByPhoneNumber(phone);

				if (phoneEntity.isPresent() && phoneNumbers.hasNext()) {
					PhoneNumberMatch pnm2 = phoneNumbers.next();
					// if the number doesn't exist then add new tweet entity
					if (!isPhoneNumberExists(pnm2.number().getNationalNumber())) {
						saveTweetEntity(tweet, city, queryHistory.getResource(), phoneNumbers, phone);
						break;
					} 
				} else if (!phoneEntity.isPresent()) {
					saveTweetEntity(tweet, city, queryHistory.getResource(), phoneNumbers, phone);
				}
			}
		});
	}

	private void saveTweetEntity(Status tweet, String city, String resource, Iterator<PhoneNumberMatch> phoneNumbers,
			long phone) {
		TweetEntity tweetToBeSaved = new TweetEntity();
		tweetToBeSaved.setTweetId(tweet.getId());
		tweetToBeSaved.setCreatedAt(tweet.getCreatedAt());
		tweetToBeSaved.setUserName(tweet.getUser().getScreenName());
		tweetToBeSaved.setText(extractRelevantTextFromTweet(tweet).trim());
		tweetToBeSaved.setResource(resource.toLowerCase());

		// save all the cities and get the desired set for tweet
		Set<String> locations = sentenceDetectorNLP.extractLocationData(tweetToBeSaved.getText());

		if (!locations.contains(city.toLowerCase()))
			locations.add(city.toLowerCase());

		Set<CityEntity> cities = locations.stream().map(location -> {
			Optional<CityEntity> c = cityRepo.findByCity(location.toLowerCase());
			if (c.isPresent())
				return c.get();
			return cityRepo.save(new CityEntity(location.toLowerCase()));
		}).collect(Collectors.toSet());
		tweetToBeSaved.setCities(cities);
		
		// save all the resources and get the desired set for tweet
		

		// Save it to DB;
		TweetEntity savedEntity = tweetRepo.save(tweetToBeSaved);

		checkAndAddPhoneNumber(savedEntity, phone);

		while (phoneNumbers.hasNext()) {
			checkAndAddPhoneNumber(savedEntity, phoneNumbers.next().number().getNationalNumber());
		}

		log.info("Saved tweet entity successfully: {}", savedEntity);
	}

	private Date updateDateAsIST(Date date) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		cal.setTime(date);
		cal.add(Calendar.HOUR, 5);
		cal.add(Calendar.MINUTE, 30);
		return cal.getTime();
	}

	private void checkAndAddPhoneNumber(TweetEntity savedEntity, long phone) {
		Optional<PhoneEntity> phoneEntity = phoneRepo.findByPhoneNumber(phone);

		if (!phoneEntity.isPresent())
			log.info("Saved phone number successfully: {}", phoneRepo.save(new PhoneEntity(phone, savedEntity)));
		else
			log.info("Phone number {} already exists", phone);

	}

	private String extractRelevantTextFromTweet(Status tweet) {
		UserMentionEntity[] mentions = tweet.getUserMentionEntities();
		return removeMentionsFromText(mentions, tweet);
	}

	private String removeMentionsFromText(UserMentionEntity[] mentions, Status tweet) {
		// if retweeted status is present then
		// fetch the text from that otherwise default tweet text.
		String resultText = tweet.getRetweetedStatus() != null ? tweet.getRetweetedStatus().getText() : tweet.getText();
		for (UserMentionEntity mention : mentions) {
			resultText = resultText.replaceAll("@" + mention.getText(), "");
		}
		return resultText.replaceAll("RT", "");
	}

	private boolean isPhoneNumberExists(long numberToMatch) {
		return phoneRepo.findByPhoneNumber(numberToMatch).isPresent();
	}
}