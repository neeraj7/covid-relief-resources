package com.covid.relief.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "resources")
public class ResourceEntity extends BasicEntity {
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "tweet_id", nullable = false)
	private TweetEntity tweet;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TweetEntity getTweet() {
		return tweet;
	}

	public void setTweet(TweetEntity tweet) {
		this.tweet = tweet;
	}
	
}