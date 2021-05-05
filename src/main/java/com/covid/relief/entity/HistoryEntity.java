package com.covid.relief.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "history")
public class HistoryEntity extends BasicEntity {
	
	private long since;

	public long getSince() {
		return since;
	}

	public void setSince(long since) {
		this.since = since;
	}
	
}