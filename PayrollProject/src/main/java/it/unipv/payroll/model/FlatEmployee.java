package it.unipv.payroll.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue(value = "Flat")
public class FlatEmployee extends Employee{

	private float salary;
	@OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
	private List<SalesReceipt> postedSalesReceipt;	

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}
	public List<SalesReceipt> getPostedSalesReceipt() {
		return postedSalesReceipt;
	}
	public void setPostedSalesReceipt(List<SalesReceipt> postedSalesReceipt) {
		this.postedSalesReceipt = postedSalesReceipt;
	}
}
