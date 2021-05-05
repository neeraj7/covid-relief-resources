package com.covid.relief.dto;

import java.util.Date;
import java.util.Set;

public class Tweet extends Basic {
	
	private long tweetId;
	
	private Date createdAt;
	
	private String text;
	
	private Set<Phone> contacts;
	
	private String resource;
	
	private String userEntity;
	
	private Set<City> cities;
	
	private String older;
	
	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
	
	public String getOlder() {
		return older;
	}

	public void setOlder(String older) {
		this.older = older;
	}

	public String getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(String userEntity) {
		this.userEntity = userEntity;
	}

	public Set<City> getCities() {
		return cities;
	}

	public void setCities(Set<City> cities) {
		this.cities = cities;
	}

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

}