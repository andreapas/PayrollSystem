package it.unipv.payroll.view;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import it.unipv.payroll.controller.CredentialsController;
import it.unipv.payroll.model.Credentials;

@Named
@ManagedBean
@RequestScoped
public class CredentialsBean implements Serializable {

	@Inject
	CredentialsController credentialsController;

	private Credentials login;
	private String code;
	private String password;

//	public String getUsername() {
//		return username;
//	}

	public void setCode(String username) {
		this.code = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password=password;
		
	}
	
	public String addLogin() {
		return credentialsController.add(login);
	}

	public String removeLogin() {
		return credentialsController.remove(code);
	}
	public String logout() {
	    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    session.invalidate();
	    return "/unifiedIndex.xhtml?faces-redirect=true";
	}

//	public boolean isUserLoggedIn() {
//	    String user = this.getUsername();
//	    boolean result = !((user == null)|| user.isEmpty());
//	    return result;
//	}

	public String getUsername() {
	    code = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
	    return code;
	}    
}
