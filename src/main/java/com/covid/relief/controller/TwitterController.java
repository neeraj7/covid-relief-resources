package com.covid.relief.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.covid.relief.dto.Tweet;
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

	@Autowired
	private TwitterService twitterService;

	@GetMapping
	public List<Tweet> getTweetsByHashtag(@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "resource", required = false) String resource) {
		return twitterService.getAllSavedTweets(city, resource);
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteTweetById(@PathVariable UUID id) {
		return twitterService.deleteTweetById(id);
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