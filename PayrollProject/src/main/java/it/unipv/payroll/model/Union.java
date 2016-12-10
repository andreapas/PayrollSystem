package it.unipv.payroll.model;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="unions")
@RequestScoped
public class Union implements Serializable{

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
