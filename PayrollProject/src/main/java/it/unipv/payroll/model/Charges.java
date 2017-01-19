package it.unipv.payroll.model;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value="Charge")
public class Charges extends Transaction{

	@ManyToOne(cascade={CascadeType.MERGE}, targetEntity=Employee.class)
	@JoinColumn(name="employee_code")
	private IEmployee employee;
	private String fromUnionName;
	
	public String getFromUnionName() {
		return fromUnionName;
	}
	public void setFromUnionName(String fromUnionName) {
		this.fromUnionName = fromUnionName;
	}
	public IEmployee getEmployee() {
		return employee;
	}
	public void setEmployee(IEmployee employee) {
		this.employee = employee;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + ((fromUnionName == null) ? 0 : fromUnionName.hashCode());
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
		Charges other = (Charges) obj;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		if (fromUnionName == null) {
			if (other.fromUnionName != null)
				return false;
		} else if (!fromUnionName.equals(other.fromUnionName))
			return false;
		return true;
	}
	
	
}
