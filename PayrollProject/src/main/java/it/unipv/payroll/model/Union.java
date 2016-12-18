package it.unipv.payroll.model;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="unions")
public class Union implements Serializable{

	@Id
	private String unionName;
	
	private double weeklyRate;
	
	@OneToMany(mappedBy = "union", fetch = FetchType.EAGER)
	private List<Employee> associates;	
	
	public List<Employee> getAssociates() {
		return associates;
	}

	public void setAssociates(List<Employee> associates) {
		this.associates = associates;
	}

	public void setUnionName(String unionName) {
		this.unionName = unionName;
	}

	public void setWeeklyRate(double weeklyRate) {
		this.weeklyRate = weeklyRate;
	}

	public String getUnionName() {
		
		return unionName;
	}

	public double getWeeklyRate() {
		
		return weeklyRate;
	}
	
}
