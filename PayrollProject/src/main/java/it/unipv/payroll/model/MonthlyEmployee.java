package it.unipv.payroll.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Monthly")
public class MonthlyEmployee extends Employee {

	private float salary;

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}
	
	
	
//	@OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
//	private List<TimeCard> postedTimeCards;	
//	
//	public List<TimeCard> getPostedTimeCards() {
//		return postedTimeCards;
//	}
//	public void setPostedTimeCards(List<TimeCard> postedTimeCards) {
//		this.postedTimeCards = postedTimeCards;
//	}
}
