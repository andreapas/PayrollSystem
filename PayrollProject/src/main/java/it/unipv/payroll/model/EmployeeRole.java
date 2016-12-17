package it.unipv.payroll.model;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.unipv.payroll.utils.Role;

@Entity
@Table(name="employee_roles")
@RequestScoped
public class EmployeeRole implements Serializable{

	@Id
	private String username;
	
	private String role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	
	
}
