package it.unipv.payroll.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue(value = "FullTime")
public class FullTimeEmployee extends Employee{

	private float salary;
	private int commissionRate;

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public int getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(int commissionRate) {
		this.commissionRate = commissionRate;
	}
	
}
