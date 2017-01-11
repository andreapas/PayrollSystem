package it.unipv.payroll.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
//import javax.servlet.http.HttpSession;

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
//	    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//	    session.invalidate();
	    return "/index.xhtml?faces-redirect=true";
	}

	public Credentials getOldLoginCredentials() {
		return oldLoginCredentials;
	}
	public void setLogin(Credentials login) {
		this.login = login;
	}
	public void setCode(String username) {
		login.setCode(username);
	}

	private void setPassword(String password) {
		login.setPassword(password);
		
	}
	public Credentials getNewLoginCredentials() {
		return newLoginCredentials;
	}
	
	public void setNewLoginCredentials(Credentials newLoginCredentials) {
		this.newLoginCredentials = newLoginCredentials;
	}
	
	public String generatePassword(){
		String generated=sessionManagementController.generatePassword();
		setPassword(generated);
		return generated;
	}
	//TODO: to test
	public String modifyPassword(){
		try {
			oldLoginCredentials.setCode(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
			newLoginCredentials.setCode(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
		} catch (NullPointerException e1) {
			System.out.println("Null face context returned, testing run detected");
		}
		
		FacesMessage message;
		
		if(sessionManagementController.areValidCredential(oldLoginCredentials.getCode(), oldLoginCredentials.getPassword())){
			sessionManagementController.update(newLoginCredentials);
	        message= new FacesMessage(FacesMessage.SEVERITY_INFO,"Success!",  "Password changed successfully") ;

		}else{
	        message= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error!",  "Wrong Password inserted. Please, try again") ;

		}
		
		try{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,message);
			
			
		}catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}

		return null;
	}
	
	public String addLogin() {
		return sessionManagementController.add(login);
	}

	public String removeLogin(String code) {
		return sessionManagementController.remove(code);
	}

}
