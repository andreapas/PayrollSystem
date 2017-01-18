package it.unipv.payroll.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "employee_type")
public abstract class Employee implements Serializable, IEmployee{

	@Id
	private String code;
	
	private String name;
	private String surname;
	private String email;
//	private String address;
	@ManyToOne/*(cascade={CascadeType.ALL})*/
	@JoinColumn(name="union_name")
	private Union union;
	
	@OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade={CascadeType.ALL})
	private List<Transactions> transactions;	

	private String address;
	private String payment_method;
	private String payment_method_details;
	
	private String role;
	
	
	
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IEmployee#getCode()
	 */
	@Override
	public String getCode() {
		return code;
	}
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IEmployee#setCode(java.lang.String)
	 */
	@Override
	public void setCode(String code) {
		this.code = code;
	}
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IEmployee#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IEmployee#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IEmployee#getSurname()
	 */
	@Override
	public String getSurname() {
		return surname;
	}
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IEmployee#setSurname(java.lang.String)
	 */
	@Override
	public void setSurname(String surname) {
		this.surname = surname;
	}
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IEmployee#getEmail()
	 */
	@Override
	public String getEmail() {
		return email;
	}
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IEmployee#setEmail(java.lang.String)
	 */
	@Override
	public void setEmail(String email) {
		this.email = email;
	}
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IEmployee#getUnion()
	 */
	@Override
	public Union getUnion() {
		return union;
	}
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IEmployee#setUnion(it.unipv.payroll.model.Union)
	 */
	@Override
	public void setUnion(Union union) {
		this.union = union;
	}
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IEmployee#getPayment_method()
	 */
	@Override
	public String getPayment_method() {
		return payment_method;
	}
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IEmployee#setPayment_method(java.lang.String)
	 */
	@Override
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IEmployee#getRole()
	 */
	@Override
	public String getRole() {
		return role;
	}
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IEmployee#setRole(java.lang.String)
	 */
	@Override
	public void setRole(String role) {
		this.role = role;
	}
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IEmployee#getAddress()
	 */
	@Override
	public String getAddress() {
		return address;
	}
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IEmployee#setAddress(java.lang.String)
	 */
	@Override
	public void setAddress(String address) {
		this.address = address;
	}
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IEmployee#getTransactions()
	 */
	@Override
	public List<Transactions> getTransactions() {
		return transactions;
	}
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IEmployee#setTransactions(java.util.List)
	 */
	@Override
	public void setTransactions(List<Transactions> transactions) {
		this.transactions = transactions;
	}
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IEmployee#getPayment_method_details()
	 */
	@Override
	public String getPayment_method_details() {
		return payment_method_details;
	}
	/* (non-Javadoc)
	 * @see it.unipv.payroll.model.IEmployee#setPayment_method_details(java.lang.String)
	 */
	@Override
	public void setPayment_method_details(String payment_method_details) {
		this.payment_method_details = payment_method_details;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((payment_method == null) ? 0 : payment_method.hashCode());
		result = prime * result + ((payment_method_details == null) ? 0 : payment_method_details.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + ((transactions == null) ? 0 : transactions.hashCode());
		result = prime * result + ((union == null) ? 0 : union.hashCode());
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
		Employee other = (Employee) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (payment_method == null) {
			if (other.payment_method != null)
				return false;
		} else if (!payment_method.equals(other.payment_method))
			return false;
		if (payment_method_details == null) {
			if (other.payment_method_details != null)
				return false;
		} else if (!payment_method_details.equals(other.payment_method_details))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (transactions == null) {
			if (other.transactions != null)
				return false;
		} else if (!transactions.equals(other.transactions))
			return false;
		if (union == null) {
			if (other.union != null)
				return false;
		} else if (!union.equals(other.union))
			return false;
		return true;
	}
	
	
	
}
