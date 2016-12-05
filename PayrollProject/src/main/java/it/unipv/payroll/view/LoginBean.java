package it.unipv.payroll.view;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
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
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String login() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(loginController.areValidCredential(username, password)){
			login.setUsername(username);
			login.setHashPassword(password);
			context.getExternalContext().getSessionMap().put("user", login);
	        return "index.xhtml";
		}
		
		context.addMessage(null, new FacesMessage("Wrong Username or Password. Try again"));

		return null;
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login.xhtml";
	}
}
