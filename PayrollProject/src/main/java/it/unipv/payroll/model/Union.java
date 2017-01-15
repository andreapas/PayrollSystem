package it.unipv.payroll.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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
	
	private float weeklyRate;
	
	@OneToMany(mappedBy = "union", fetch = FetchType.EAGER)
	private List<Employee> associates;	
	
	public List<Employee> getAssociates() {
		return associates;
	}

//	public void setAssociates(List<Employee> associates) {
//		this.associates = associates;
//	}

	public void setUnionName(String unionName) {
		this.unionName = unionName;
	}

	public void setWeeklyRate(float weeklyRate) {
		this.weeklyRate = weeklyRate;
	}

	public String getUnionName() {
		
		return unionName;
	}

	public float getWeeklyRate() {
		
		return weeklyRate;
	}
	@Override
	public boolean equals(Object obj) {
		// Basic checks.
        if (obj == this) return true;
        if (!(obj instanceof Union)) return false;

        // Property checks.
        Union other = (Union) obj;
        return Objects.equals(unionName, other.unionName)
            && Objects.equals(weeklyRate, other.weeklyRate);
	}
}
