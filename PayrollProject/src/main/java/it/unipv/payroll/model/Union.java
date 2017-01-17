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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((associates == null) ? 0 : associates.hashCode());
		result = prime * result + ((unionName == null) ? 0 : unionName.hashCode());
		result = prime * result + Float.floatToIntBits(weeklyRate);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Union other = (Union) obj;
		if (associates == null) {
			if (other.associates != null)
				return false;
		} else if (!associates.equals(other.associates))
			return false;
		if (unionName == null) {
			if (other.unionName != null)
				return false;
		} else if (!unionName.equals(other.unionName))
			return false;
		if (Float.floatToIntBits(weeklyRate) != Float.floatToIntBits(other.weeklyRate))
			return false;
		return true;
	}
}
