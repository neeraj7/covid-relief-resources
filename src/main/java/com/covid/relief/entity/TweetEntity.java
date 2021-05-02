package com.covid.relief.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tweet")
public class TweetEntity extends BasicEntity {

	private long tweetId;

	private Date createdAt;

	@Column(columnDefinition = "TEXT")
	private String text;

	private String userEntity;

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

	public String getUser() {
		return userEntity;
	}

	public void setUser(String user) {
		this.userEntity = user;
	}
}