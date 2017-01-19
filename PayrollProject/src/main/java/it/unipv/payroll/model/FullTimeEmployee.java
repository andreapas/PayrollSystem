package it.unipv.payroll.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue(value = "FullTime")
public class FullTimeEmployee extends Employee{

	private float salary;
	private int commissionRate;
	@OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade={CascadeType.ALL}, targetEntity=Sales.class)
	private List<ITransaction> sales;	

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
	
	public List<ITransaction> getSales() {
		return sales;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + commissionRate;
		result = prime * result + Float.floatToIntBits(salary);
		result = prime * result + ((sales == null) ? 0 : sales.hashCode());
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
		FullTimeEmployee other = (FullTimeEmployee) obj;
		if (commissionRate != other.commissionRate)
			return false;
		if (Float.floatToIntBits(salary) != Float.floatToIntBits(other.salary))
			return false;
		if (sales == null) {
			if (other.sales != null)
				return false;
		} else if (!sales.equals(other.sales))
			return false;
		return true;
	}
	
}
