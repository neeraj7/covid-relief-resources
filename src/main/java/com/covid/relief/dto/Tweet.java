package com.covid.relief.dto;

import java.util.Date;

public class Tweet extends Basic {
	
	private long tweetId;
	
	private Date createdAt;
	
	private String text;
	
	private long phoneNumber;
	
	private String additionalNumbers;
	
	private String userEntity;

	public long getTweetId() {
		return tweetId;
	}

	public void setTweetId(long tweetId) {
		this.tweetId = tweetId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAdditionalNumbers() {
		return additionalNumbers;
	}

	public void setAdditionalNumbers(String additionalNumbers) {
		this.additionalNumbers = additionalNumbers;
	}

	public String getUser() {
		return userEntity;
	}

	public void setUser(String user) {
		this.userEntity = user;
	}
}