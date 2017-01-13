package it.unipv.payroll.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue(value = "PartTime")
public class PartTimeEmployee extends Employee {

	private float hourlyRate;
	
	public float getHourlyRate() {
		return hourlyRate;
	}
	public void setHourlyRate(float hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	
}
