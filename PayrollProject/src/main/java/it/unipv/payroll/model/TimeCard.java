package it.unipv.payroll.model;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="timeCards")
@RequestScoped
public class TimeCard implements Serializable {

	
	@Id
	private int postId;
	private String employeeCode;
	private int hoursWorked;
	private long data;
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public int getHoursWorked() {
		return hoursWorked;
	}
	public void setHoursWorked(int hoursWorked) {
		this.hoursWorked = hoursWorked;
	}
	public long getData() {
		return data;
	}
	public void setData(long data) {
		this.data = data;
	}
	
	
	
}
