package it.unipv.payroll.view;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.LoginController;
import it.unipv.payroll.model.Login;

@Named
@ManagedBean
@RequestScoped
public class CredentialBean implements Serializable {

	@Inject
	LoginController loginController;

	private Login login;
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password=password;
		
	}
	
	public String addLogin() {
		return loginController.add(login);
	}

	public String removeLogin() {
		return loginController.remove(username);
	}
}
