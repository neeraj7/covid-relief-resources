package com.covid.relief.dto;

import java.util.Date;
import java.util.UUID;

public class Basic {
	
	private UUID id;
	
	private Date created;
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}