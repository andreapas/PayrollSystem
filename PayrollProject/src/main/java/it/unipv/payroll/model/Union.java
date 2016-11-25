package it.unipv.payroll.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="unions")
public class Union {

	@Id
	private String unionName = null;
	
	private double unionFee = -1;
	
	public void setUnion(String unionName, double unionFee) {
		
		this.unionName = unionName;
		this.unionFee = unionFee;
	}

	public String getUnionName() {
		
		return unionName;
	}

	public double getUnionFee() {
		
		return unionFee;
	}

}
