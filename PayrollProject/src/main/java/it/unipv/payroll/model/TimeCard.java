package it.unipv.payroll.model;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="timeCards")
public class TimeCard implements Serializable {

	
	@Id
	private int postId;
	
	@ManyToOne/*(cascade={CascadeType.ALL})*/
	@JoinColumn(name="employee_code")
	private Employee employee;
	
	private int hoursWorked;
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
	public void setEmployee(Employee employeeCode) {
		this.employee = employee;
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
