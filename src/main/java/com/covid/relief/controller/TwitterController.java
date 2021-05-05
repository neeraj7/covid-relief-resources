package com.covid.relief.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.covid.relief.dto.Tweet;
import com.covid.relief.init.AppInitializer;
import com.covid.relief.service.TwitterService;

import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

@RestController
@RequestMapping("/twitter")
public class TwitterController {

	@Autowired
	private TwitterService twitterService;
	
	@Autowired
	private AppInitializer initializer;
	
	@GetMapping
	public List<Tweet> getTweetsByHashtag(@RequestParam("city") String city, @RequestParam("resource") String resource) 
	{	
		return twitterService.getAllSavedTweets(city, resource);
	}
	
	@GetMapping("/query")
	public QueryResult getQueryResult(@RequestParam("city") String city, @RequestParam("resource") String resource) {
		return initializer.filterAndSaveTweetsInDB(city, resource);
	}
	
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