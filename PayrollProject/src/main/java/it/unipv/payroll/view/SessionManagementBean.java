package it.unipv.payroll.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import it.unipv.payroll.controller.SessionManagementController;
import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.model.Credentials;
import it.unipv.payroll.model.Employee;

@Named
@ManagedBean
@RequestScoped
public class SessionManagementBean implements Serializable {

	@Inject
	SessionManagementController sessionManagementController;
	@Inject
	EmployeeController emController;


	private Credentials login;
	private String code;
	private String password;
	private Employee loggedUser;

	@PostConstruct
	public void init(){
		loggedUser = emController.find(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());

	}
	public Employee getLoggedUser() {
		return loggedUser;
	}

	public String logout() {
	    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    session.invalidate();
	    return "/unifiedIndex.xhtml?faces-redirect=true";
	}

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
		return sessionManagementController.add(login);
	}

	public String removeLogin() {
		return sessionManagementController.remove(code);
	}

}
