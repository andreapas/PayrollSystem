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
	public IEmployee getEmployee() {
		return employee;
	}
	public void setEmployee(IEmployee employee) {
		this.employee = employee;
	}
	
}
