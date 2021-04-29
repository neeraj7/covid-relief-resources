package com.covid.relief.dto;

public class Working {
	
	private boolean isVerified;
	
	private String verifiedBy;
	
	private int itWorkedCount;
	
	private int removeCount;

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

	public int getItWorkedCount() {
		return itWorkedCount;
	}

	public void setItWorkedCount(int itWorkedCount) {
		this.itWorkedCount = itWorkedCount;
	}

	public int getRemoveCount() {
		return removeCount;
	}

	public void setRemoveCount(int removeCount) {
		this.removeCount = removeCount;
	}
	
	
	
}