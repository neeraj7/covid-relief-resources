package com.covid.relief.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@RestController
@RequestMapping("/twitter")
public class TwitterController {
	
//	@Value("${oauth.consumerKey}")
//	private String consumerKey;
//	
//	@Value("${oauth.consumerSecret}")
//	private String consumerSecret;
//	
//	@Value("${oauth.accessToken}")
//	private String accessToken;
//	
//	@Value("${oauth.accessTokenSecret}")
//	private String accessTokenSecret;
	
	@GetMapping
	public List<String> getTweetsByHashtag() {
//		ConfigurationBuilder cb = new ConfigurationBuilder();
//		cb.setDebugEnabled(true)
//		  .setOAuthConsumerKey(consumerKey)
//		  .setOAuthConsumerSecret(consumerSecret)
//		  .setOAuthAccessToken(accessToken)
//		  .setOAuthAccessTokenSecret(accessTokenSecret);
//		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = TwitterFactory.getSingleton();
		Query query = new Query("covid19");
	    try {
			QueryResult result = twitter.search(query);
			return result.getTweets().stream()
					.map(item -> item.getText())
					.collect(Collectors.toList());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	    
	}

}
