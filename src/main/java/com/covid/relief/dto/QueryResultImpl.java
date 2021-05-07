package com.covid.relief.dto;

import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;

public class QueryResultImpl implements QueryResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RateLimitStatusImpl limitStatus;
	
	public void setRateLimitStatus(RateLimitStatusImpl status) {
		this.limitStatus = status;
	}

	@Override
	public RateLimitStatus getRateLimitStatus() {
		// TODO Auto-generated method stub
		return limitStatus;
	}

	@Override
	public int getAccessLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getSinceId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getMaxId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getRefreshURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getCompletedIn() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Status> getTweets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query nextQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

}
