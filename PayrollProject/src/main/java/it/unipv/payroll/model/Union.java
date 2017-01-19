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
public class Union implements Serializable, IUnion{

	@Id
	private String unionName;
	
	private float weeklyRate;
	
	@OneToMany(mappedBy = "union", fetch = FetchType.EAGER)
	private List<Employee> associates;	
	
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IUnion#getAssociates()
	 */
	@Override
	public List<Employee> getAssociates() {
		return associates;
	}

	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IUnion#setUnionName(java.lang.String)
	 */
	@Override
	public void setUnionName(String unionName) {
		this.unionName = unionName;
	}

	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IUnion#setWeeklyRate(float)
	 */
	@Override
	public void setWeeklyRate(float weeklyRate) {
		this.weeklyRate = weeklyRate;
	}

	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IUnion#getUnionName()
	 */
	@Override
	public String getUnionName() {
		
		return unionName;
	}

	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IUnion#getWeeklyRate()
	 */
	@Override
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
		// Basic checks.
        if (obj == this) return true;
        if (!(obj instanceof Union)) return false;

        // Property checks.
        Union other = (Union) obj;
        return Objects.equals(unionName, other.unionName)
            && Objects.equals(weeklyRate, other.weeklyRate);
	}
}
