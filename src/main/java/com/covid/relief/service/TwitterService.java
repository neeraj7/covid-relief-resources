package com.covid.relief.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covid.relief.entity.TweetEntity;
import com.covid.relief.repository.TweetRepository;
import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.UserMentionEntity;

@Service
public class TwitterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterService.class);

	@Autowired
	private TweetRepository tweetRepo;

	public List<TweetEntity> getAllSavedTweets(String city, String resource) {
		filterTweets(city, resource);
		return tweetRepo.findAll();
	}

	public QueryResult filterTweets(String city, String resource) {
		// Get twitter connection
		Twitter twitter = TwitterFactory.getSingleton();

		// To be sent to twitter api
		Query query = new Query("verified " + city + " " + resource
				+ " -'not verified' -'un verified' -urgent -unverified -needed -required -need -needs since:"
				+ LocalDate.now());
		QueryResult result = null;
		try {
			// Twitter query search result
			result = twitter.search(query);

			// Filter out all the tweets which are not stored in DB
			result.getTweets().stream().filter(t -> tweetRepo.findByTweetId(t.getId()).isEmpty()).forEach(tweet -> {

				String tweetText = tweet.getRetweetedStatus() != null ? tweet.getRetweetedStatus().getText()
						: tweet.getText();

				// Iterate over all the filtered tweets
				Iterator<PhoneNumberMatch> phoneNumbers = PhoneNumberUtil.getInstance().findNumbers(tweetText, "IN")
						.iterator();

				// Need to check by phone number in DB
				while (phoneNumbers.hasNext()) {
					PhoneNumberMatch pnm = phoneNumbers.next();
					long phone = pnm.number().getNationalNumber();

					Optional<TweetEntity> tweetEntity = tweetRepo.findByPhoneNumber(phone);

					if (tweetEntity.isPresent() && phoneNumbers.hasNext()) {
						PhoneNumberMatch pnm2 = phoneNumbers.next();
						// if the number doesn't exist then add new tweet entity
						if (!isPhoneNumberExists(tweetEntity.get().getAdditionalNumbers().split(","),
								String.valueOf(pnm2.number().getNationalNumber()))) {
							saveTweetEntity(tweet, phoneNumbers, phone);
							break;
						}
					} else if (tweetEntity.isEmpty()) {
						saveTweetEntity(tweet, phoneNumbers, phone);
					}
				}
			});

//			SentenceDetectorNLP nlp = new SentenceDetectorNLP();
//			nlp.extractLocationData(
//					tweet.getRetweetedStatus() != null ? tweet.getRetweetedStatus().getText() : tweet.getText());
//
			return result;
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private void saveTweetEntity(Status tweet, Iterator<PhoneNumberMatch> phoneNumbers, long phone) {
		TweetEntity tweetToBeSaved = new TweetEntity();
		tweetToBeSaved.setTweetId(tweet.getId());
		tweetToBeSaved.setCreatedAt(tweet.getCreatedAt());
		tweetToBeSaved.setPhoneNumber(phone);
		tweetToBeSaved.setUser(tweet.getUser().getScreenName());
		tweetToBeSaved.setAdditionalNumbers(phoneNumbers.hasNext() ? getAdditionalNumbers(phoneNumbers) : "");
		tweetToBeSaved.setText(extractRelevantTextFromTweet(tweet, phone));

		// Save it to DB;
		TweetEntity savedEntity = tweetRepo.save(tweetToBeSaved);
		LOGGER.info("Saved tweet entity successfully: {}", savedEntity);
	}

	private String extractRelevantTextFromTweet(Status tweet, long phone) {
		UserMentionEntity[] mentions = tweet.getUserMentionEntities();
		String text = removeMentionsFromText(mentions, tweet).replaceAll(String.valueOf(phone), "").replaceAll("\\+91",
				"");
		return text;
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

	private String getAdditionalNumbers(Iterator<PhoneNumberMatch> itr) {
		String result = String.valueOf(itr.next().number().getNationalNumber());
		while (itr.hasNext()) {
			result.concat("," + String.valueOf(itr.next().number().getNationalNumber()));
		}
		return result;
	}

	private boolean isPhoneNumberExists(String[] numbers, String numberToMatch) {
		return Arrays.asList(numbers).contains(numberToMatch);
	}

}
