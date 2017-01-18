package it.unipv.payroll.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import it.unipv.payroll.controller.SessionManagementController;
import it.unipv.payroll.model.Credentials;

@Named
@SessionScoped
public class SessionManagementBean implements Serializable {

	@Inject
	SessionManagementController sessionManagementController;


	private Credentials login;
	private Credentials newLoginCredentials;
	private Credentials oldLoginCredentials;

	@PostConstruct
	public void init(){
		login= new Credentials();
		newLoginCredentials= new Credentials();
		oldLoginCredentials=new Credentials();
	}
	
	public String logout() {
	    try {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.invalidate();
		} catch (NullPointerException e) {
			System.out.println("Null face context detected, maybe this bean has been run for testing");
		}
	    return "/index.xhtml?faces-redirect=true";
	}

	public Credentials getOldLoginCredentials() {
		return oldLoginCredentials;
	}
	public Credentials getNewLoginCredentials() {
		return newLoginCredentials;
	}
	
	public void setNewLoginCredentials(Credentials newLoginCredentials) {
		this.newLoginCredentials = newLoginCredentials;
	}
	
	//TODO: to test
	public void modifyPassword(){
		oldLoginCredentials.setCode(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
		newLoginCredentials.setCode(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
		
		try{
			sessionManagementController.changePassword(oldLoginCredentials,newLoginCredentials);
			FacesMessage message= new FacesMessage(FacesMessage.SEVERITY_INFO,"Success!",  "Password changed successfully") ;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,message);
		}catch (Exception e) {
			FacesMessage message= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error!",  e.getMessage()) ;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,message);
		}
		
		
			
	}
	
}
