package com.covid.relief.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.covid.relief.dto.Address;
import com.covid.relief.dto.Medicines;

@Entity
@Table(name = "resources")
public class ResourceEntity extends BasicEntity {
	
	private String name;
	
	@Embedded
	private Address address;
	
	private Number contactNo;
	
	private boolean oxygenCylinders;
	
	private boolean oxygenRefill;

	@Embedded
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

	public Number getContactNo() {
		return contactNo;
	}

	public void setContactNo(Number contactNo) {
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