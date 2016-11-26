package it.unipv.payroll.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="payroll")
public class Payroll implements Serializable{

	@Id
	private int id;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Payroll [message=" + message + "]";
	}
	
	
}
