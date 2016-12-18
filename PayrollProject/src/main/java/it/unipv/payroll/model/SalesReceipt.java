package it.unipv.payroll.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="salesReceipts")
public class SalesReceipt implements Serializable{

	
	@Id
	private int postId;
	
	@ManyToOne
	@JoinColumn(name="employee_code")
	private Employee employee;
	
	private int amount;
	private long data;
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public long getData() {
		return data;
	}
	public void setData(long data) {
		this.data = data;
	}
	
}
