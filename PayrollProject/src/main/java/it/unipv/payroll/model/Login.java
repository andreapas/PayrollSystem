package it.unipv.payroll.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="credentials")
public class Login implements Serializable{

	@Id
	private String hashUsername;
	
	private String hashPassword;

	public String getHashUsername() {
		return hashUsername;
	}

	public void setHashUsername(String hashUsername) {
		this.hashUsername = hashUsername;
	}

	public String getHashPassword() {
		return hashPassword;
	}

	public void setHashPassword(String hashPassword) {
		this.hashPassword = hashPassword;
	}

	
	
	
	
}
