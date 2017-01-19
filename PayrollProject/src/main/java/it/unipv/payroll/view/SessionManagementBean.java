package it.unipv.payroll.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import it.unipv.payroll.controller.SessionManagementController;
import it.unipv.payroll.model.Credentials;
import it.unipv.payroll.model.ICredentials;

@Named
@RequestScoped
public class SessionManagementBean implements Serializable {

	@Inject
	SessionManagementController sessionManagementController;
	
	private ICredentials login;
	private Credentials newLoginCredentials;
	private ICredentials oldLoginCredentials;

	@PostConstruct
	public void init(){
		login= new Credentials();
		newLoginCredentials= new Credentials();
		oldLoginCredentials=new Credentials();
	}
	
	public String logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.invalidate();
	    return "/index.xhtml?faces-redirect=true";
	}

	public ICredentials getOldLoginCredentials() {
		return oldLoginCredentials;
	}
	public ICredentials getNewLoginCredentials() {
		return newLoginCredentials;
	}
	
	public void setNewLoginCredentials(Credentials newLoginCredentials) {
		this.newLoginCredentials = newLoginCredentials;
	}
	
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
