package com.covid.relief.dto;

public class Medicines {
	
	private boolean remdesivir;
	
	private boolean fabiflu;
	
	private boolean favipiravir;
	
	private boolean tucilizumab;

	public boolean isRemdesivir() {
		return remdesivir;
	}

	public void setRemdesivir(boolean remdesivir) {
		this.remdesivir = remdesivir;
	}

	public boolean isFabiflu() {
		return fabiflu;
	}

	public void setFabiflu(boolean fabiflu) {
		this.fabiflu = fabiflu;
	}

	public boolean isFavipiravir() {
		return favipiravir;
	}

	public void setFavipiravir(boolean favipiravir) {
		this.favipiravir = favipiravir;
	}

	public boolean isTucilizumab() {
		return tucilizumab;
	}

	public void setTucilizumab(boolean tucilizumab) {
		this.tucilizumab = tucilizumab;
	}
	
	
}