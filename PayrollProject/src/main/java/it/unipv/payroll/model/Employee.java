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
public abstract class Employee implements Serializable{

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

    @MapsId 
    @OneToOne(mappedBy = "employee")
    @JoinColumn(name = "code") 
	private Address address=new Address();
	private String payment_method;
	
	private String role;
	
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Union getUnion() {
		return union;
	}
	public void setUnion(Union union) {
		this.union = union;
	}
	public String getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<Transactions> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transactions> transactions) {
		this.transactions = transactions;
	}
	
	
	
}
