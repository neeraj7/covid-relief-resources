package com.covid.relief.dto;

public class QueryHistory extends Basic {

	private String resource;
	
	private long sinceId;

	public QueryHistory(String resource, long sinceId) {
		this.resource = resource;
		this.sinceId = sinceId;
	}

	public QueryHistory(String resource) {
		this.resource = resource;
	}

	public long getSinceId() {
		return sinceId;
	}

	public void setSinceId(long sinceId) {
		this.sinceId = sinceId;
	}
	
	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
	
}