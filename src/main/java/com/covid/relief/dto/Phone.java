package com.covid.relief.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Phone extends Basic {
	
	private long phoneNumber;
	
	private Tweet tweet;

	public Phone(long phone, Tweet tweet) {
		this.phoneNumber = phone;
		this.tweet = tweet;
	}

	@Override
	public String toString() {
		return "Phone [phoneNumber=" + phoneNumber + ", tweet=" + tweet + ", getId()=" + getId() + ", getCreated()="
				+ getCreated() + ", getClass()=" + getClass() + "]";
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Tweet getTweet() {
		return tweet;
	}

	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}
	
}
