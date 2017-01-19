package it.unipv.payroll.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue(value = "PartTime")
public class PartTimeEmployee extends Employee {

	private float hourlyRate;
	@OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade={CascadeType.ALL}, targetEntity=TimeCard.class)
	private List<ITransaction> timeCards;	
	
	public float getHourlyRate() {
		return hourlyRate;
	}
	public void setHourlyRate(float hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	public List<ITransaction> getTimeCards() {
		return timeCards;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Float.floatToIntBits(hourlyRate);
		result = prime * result + ((timeCards == null) ? 0 : timeCards.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartTimeEmployee other = (PartTimeEmployee) obj;
		if (Float.floatToIntBits(hourlyRate) != Float.floatToIntBits(other.hourlyRate))
			return false;
		if (timeCards == null) {
			if (other.timeCards != null)
				return false;
		} else if (!timeCards.equals(other.timeCards))
			return false;
		return true;
	}
	
	
}
