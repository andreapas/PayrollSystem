package it.unipv.payroll.view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.LoginController;
import it.unipv.payroll.model.Login;

@Named
@ManagedBean
@RequestScoped
public class LoginBean implements Serializable {

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
		login.setUsername(username);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		login.setHashPassword(password);
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public String login() {
		return loginController.login(login);
	}

	public String logout() {
		return loginController.logout();
	}
}
