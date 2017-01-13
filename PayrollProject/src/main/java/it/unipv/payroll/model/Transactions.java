package it.unipv.payroll.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="transactions")
public class Transactions implements Serializable {
	
	@Id 
	private int id;
	
	@ManyToOne
	@JoinColumn(name="employee_code")
	private Employee employee;
	private Date date;
	private Double amount;
	private String info;
	private boolean executed;
	
	@PrePersist
    void preInsert() {
       executed=false;
    }
	
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public int getId() {
		return id;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public boolean isExecuted() {
		return executed;
	}
	public void setExecuted(boolean executed) {
		this.executed = executed;
	}
}
