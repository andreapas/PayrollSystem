package it.unipv.payroll.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usertransactions")
public class EmployeeTransactions implements Serializable{

	@Id
	private String code;
	private int fee;
	private int earned;
	

	public void setCode(String code) {
		this.code=code;
	}


	public int getFee() {
		return fee;
	}


	public void setFee(int fee) {
		this.fee = fee;
	}


	public int getEarned() {
		return earned;
	}


	public void setEarned(int earned) {
		this.earned = earned;
	}


	public String getCode() {
		return code;
	}
	
	
	
}
