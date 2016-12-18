package it.unipv.payroll.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue(value = "Weekly")
public class WeeklyEmployee extends Employee{

	private float hourlyRate;
	@OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
	private List<TimeCard> postedTimeCards;	
	
	
	
	public List<TimeCard> getPostedTimeCards() {
		return postedTimeCards;
	}
	public void setPostedTimeCards(List<TimeCard> postedTimeCards) {
		this.postedTimeCards = postedTimeCards;
	}
	
	
	public float getHourlyRate() {
		return hourlyRate;
	}
	public void setHourlyRate(float hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	
}
