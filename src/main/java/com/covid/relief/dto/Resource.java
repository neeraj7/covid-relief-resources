package com.covid.relief.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Resource extends Basic {

	@NotBlank
	private String name;
	
	@NotNull
	private Address address;
	
	@Pattern(regexp="(^$|[0-9]{10})")
	private String contactNo;
	
	private boolean oxygenCylinders;
	
	private boolean oxygenRefill;
	
	@NotNull
	private Medicines medicines;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public boolean isOxygenCylinders() {
		return oxygenCylinders;
	}

	public void setOxygenCylinders(boolean oxygenCylinders) {
		this.oxygenCylinders = oxygenCylinders;
	}

	public boolean isOxygenRefill() {
		return oxygenRefill;
	}

	public void setOxygenRefill(boolean oxygenRefill) {
		this.oxygenRefill = oxygenRefill;
	}

	public Medicines getMedicines() {
		return medicines;
	}

	public void setMedicines(Medicines medicines) {
		this.medicines = medicines;
	}
	
	
}
