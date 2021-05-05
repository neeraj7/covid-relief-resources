package com.covid.relief.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "city")
public class CityEntity extends BasicEntity {

	@Column(unique = true)
	private String city;
	
	@ManyToMany(mappedBy = "cities")
	private Set<TweetEntity> tweets;
	
	public CityEntity() {
		super();
	}

	public CityEntity(String city) {
		this.city = city;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CityEntity other = (CityEntity) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		return true;
	}

	public Set<TweetEntity> getTweets() {
		return tweets;
	}

	@Override
	public String toString() {
		return "CityEntity [city=" + city + ", tweets=" + tweets + ", getId()=" + getId() + ", getCreated()="
				+ getCreated() + "]";
	}

	public void setTweets(Set<TweetEntity> tweets) {
		this.tweets = tweets;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}