package com.covid.relief.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tweet")
public class TweetEntity extends BasicEntity {

	private long tweetId;

	private Date createdAt;

	@Column(columnDefinition = "TEXT")
	private String text;

	private String userEntity;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tweet_cities")
	private Set<CityEntity> cities;
	
	private String resource;

	public String getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(String userEntity) {
		this.userEntity = userEntity;
	}

	public Set<CityEntity> getCities() {
		return cities;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public void setCities(Set<CityEntity> cities) {
		this.cities = cities;
	}

	public long getTweetId() {
		return tweetId;
	}

	@Override
	public String toString() {
		return "TweetEntity [tweetId=" + tweetId + ", createdAt=" + createdAt + "]";
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
}