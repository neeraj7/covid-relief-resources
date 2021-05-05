package com.covid.relief.dto;

import java.util.Set;


public class City extends Basic {

	private String city;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public Set<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(Set<Tweet> tweets) {
		this.tweets = tweets;
	}

	private Set<Tweet> tweets;
	
	
}