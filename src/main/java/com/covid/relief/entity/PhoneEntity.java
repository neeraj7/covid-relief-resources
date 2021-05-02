package com.covid.relief.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "phone")
public class PhoneEntity extends BasicEntity {
	
	private long phoneNumber;
	
	@ManyToOne
	@JoinColumn(name = "tweet_id", nullable = false)
	private TweetEntity tweet;

	public PhoneEntity() {
		super();
	}

	public PhoneEntity(long phone, TweetEntity savedEntity) {
		this.phoneNumber = phone;
		this.tweet = savedEntity;
	}

	@Override
	public String toString() {
		return "PhoneEntity [phoneNumber=" + phoneNumber + "]";
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public TweetEntity getTweet() {
		return tweet;
	}

	public void setTweet(TweetEntity tweet) {
		this.tweet = tweet;
	}
	
}
