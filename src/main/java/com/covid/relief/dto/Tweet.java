package com.covid.relief.dto;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Tweet extends Basic {
	
	private long tweetId;
	
	private Date createdAt;
	
	private String text;
	
	private Set<Phone> contacts;
	
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

	public Set<Phone> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Phone> contacts) {
		this.contacts = contacts;
	}

	public String getUser() {
		return userEntity;
	}

	public void setUser(String user) {
		this.userEntity = user;
	}
}