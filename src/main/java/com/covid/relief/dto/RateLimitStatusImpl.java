package com.covid.relief.dto;

import twitter4j.RateLimitStatus;

public class RateLimitStatusImpl implements RateLimitStatus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int remaining;
	
	private int secondsUntilReset;
	
	public RateLimitStatusImpl(RateLimitStatus rateLimitStatus) {
		this.remaining = rateLimitStatus.getRemaining();
		this.secondsUntilReset = rateLimitStatus.getSecondsUntilReset();
	}

	public void setSecondsUntilReset(int secondsUntilReset) {
		this.secondsUntilReset = secondsUntilReset;
	}
	
	public void setRemaining(int remaining) {
		this.remaining = remaining;
	}

	@Override
	public int getRemaining() {
		return remaining;
	}

	@Override
	public int getLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getResetTimeInSeconds() {
		// TODO Auto-generated method stub
		return secondsUntilReset;
	}

	@Override
	public int getSecondsUntilReset() {
		// TODO Auto-generated method stub
		return secondsUntilReset;
	}
	
}