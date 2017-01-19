package it.unipv.payroll.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@DiscriminatorValue(value="Sale")
public class Sales extends Transaction {

	@ManyToOne(cascade={CascadeType.MERGE}, targetEntity=FullTimeEmployee.class)
	@JoinColumn(name="seller_code")
	private IEmployee employee;
	
	private int receipt_ID;

	public IEmployee getEmployee() {
		return employee;
	}
	public void setEmployee(IEmployee employee) {
		this.employee = employee;
	}
	public int getReceipt_ID() {
		return receipt_ID;
	}
	public void setReceipt_ID(int receipt_ID) {
		this.receipt_ID = receipt_ID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + receipt_ID;
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
		Sales other = (Sales) obj;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		if (receipt_ID != other.receipt_ID)
			return false;
		return true;
	}
	
	
	
}
