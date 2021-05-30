package com.covid.relief.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.covid.relief.dto.Tweet;
import com.covid.relief.entity.TweetEntity;
import com.covid.relief.init.AppInitializer;
import com.covid.relief.mapper.TweetMapper;
import com.covid.relief.repository.TweetRepository;
import com.covid.relief.service.TwitterService;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

@RestController
@RequestMapping("/twitter")
public class TwitterController {

	private static final Logger log = LoggerFactory.getLogger(TwitterController.class);

	@Autowired
	private TwitterService twitterService;

	@Autowired
	private TweetRepository tweetRepo;

	@GetMapping
	public List<Tweet> getTweetsByHashtag(@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "resource", required = false) String resource) {
		return twitterService.getAllSavedTweets(city, resource);
	}

	@GetMapping("delete")
	public String deleteApi() {

		Map<String, TweetEntity> unique = new HashMap<>();

		List<TweetEntity> list = tweetRepo.findAll();

		list.stream().forEach(tweet -> {
			if (unique.containsKey(tweet.getText())) {
				// duplicate found, so delete it now...
				log.info("Deleting duplicate tweet: {}", tweet.getText().substring(0, 11));
				tweetRepo.delete(tweet);

			} else {
				unique.put(tweet.getText(), tweet);
			}
		});
		return "Duplicates deleted";
	}

	@GetMapping("/oldTweets")
	public List<Tweet> getOldTweets() {
		return tweetRepo.findByCreatedAtLessThan(
				Date.from(LocalDate.now().minusWeeks(2).atStartOfDay(ZoneId.systemDefault()).toInstant()))
				.stream()
				.map(entity -> Mappers.getMapper(TweetMapper.class).toModel(entity))
				.collect(Collectors.toList());
	}
	
	@GetMapping("/deleteOld")
	public String deleteOldTweets() {
		List<TweetEntity> oldTweets = tweetRepo.findByCreatedAtLessThan(
				Date.from(LocalDate.now().minusWeeks(2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		if(oldTweets.isEmpty())
			return "Found no older tweets";
		else {
			tweetRepo.deleteAll(oldTweets);
			return "Delete 2 weeks older tweets.";
		}
	}

//	@GetMapping("/query")
//	public QueryResult getQueryResult(@RequestParam("city") String city, @RequestParam("resource") String resource) {
//		return twitterService.
//	}

	private void testTwitterStream() {
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance().addListener(new StatusListener() {
			@Override
			public void onStatus(Status status) {
				System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
				System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			}

			@Override
			public void onStallWarning(StallWarning warning) {
				System.out.println("Got stall warning:" + warning);
			}

			@Override
			public void onException(Exception ex) {
				ex.printStackTrace();
			}
		}).sample();
	}

}