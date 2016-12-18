package it.unipv.payroll.model;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="employees")
@RequestScoped
public class Employee implements Serializable{

	@Id
	private String code;
	
	private String name;
	private String surname;
	private String email;
	@ManyToOne/*(cascade={CascadeType.ALL})*/
	@JoinColumn(name="union_name")
	private Union union;
	private String payment_method;
	
	@OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
	private List<TimeCard> postedTimeCards;	
	
	public List<TimeCard> getPostedTimeCards() {
		return postedTimeCards;
	}
	public void setPostedTimeCards(List<TimeCard> postedTimeCards) {
		this.postedTimeCards = postedTimeCards;
	}
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
	
	
	
	
}
