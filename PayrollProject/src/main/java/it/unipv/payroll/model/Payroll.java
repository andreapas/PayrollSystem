package it.unipv.payroll.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="payroll")
public class Payroll implements Serializable{

	@Id
	private int id;
	
	private String message;
	
	@ManyToOne (cascade = {CascadeType.ALL})
	@JoinColumn(name="user_name")
	private PayrollUsername user_name; //Questo deve essere una Union
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getId() {
		return id;
	}
	
	public PayrollUsername getUser_name() {
		return user_name;
	}

	public void setUser_name(PayrollUsername user_name) {
		this.user_name = user_name;
	}

	@Override
	public String toString() {
		return "Payroll [message=" + message + "]";
	}
	
	
}
