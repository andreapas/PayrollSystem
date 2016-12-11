package it.unipv.payroll.view;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import it.unipv.payroll.controller.LoginController;
import it.unipv.payroll.model.Credentials;

@Named
@ManagedBean
@RequestScoped
public class CredentialBean implements Serializable {

	@Inject
	LoginController loginController;

	private Credentials login;
	private String username;
	private String password;

//	public String getUsername() {
//		return username;
//	}

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
	public String logout() {
	    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    session.invalidate();
	    return "/index.xhtml?faces-redirect=true";
	}

	public boolean isUserLoggedIn() {
	    String user = this.getUsername();
	    boolean result = !((user == null)|| user.isEmpty());
	    return result;
	}

	public String getUsername() {
	    username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
	    return username;
	}    
}
