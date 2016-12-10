package it.unipv.payroll.model;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="transactionsinfo")
@RequestScoped
public class TransactionsInfo implements Serializable {
	
	@Id 
	private int id;
	
	private String info;
	private String code;
	
	public int getId() {
		return id;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	


}
