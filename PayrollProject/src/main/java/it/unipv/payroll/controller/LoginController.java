package it.unipv.payroll.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import it.unipv.payroll.dao.LoginDAO;
import it.unipv.payroll.model.Login;

@Stateless
public class LoginController {

	@Inject LoginDAO loginDAO;
	
	private static String SUCCESS="Success";
	private static String WRONG="Wrong Username or Password";
	
	public String addLogin(Login login){
		
		loginDAO.add(login);
		return SUCCESS;
	}

	public String removeLogin(Login login) {
		loginDAO.remove(login.getUsername());
		return SUCCESS;
	}

	public String updateLogin(Login login) {
		loginDAO.update(login);
		return SUCCESS;
	}

	public String login(Login login) {
		
		Login user = loginDAO.find(login.getUsername());
		FacesContext context = FacesContext.getCurrentInstance();
		
		if (user == null) {
			context.addMessage(null, new FacesMessage("Unknown login, try again"));
			return null;
		} else if(user.getHashPassword().equals(login.getHashPassword())){
			context.getExternalContext().getSessionMap().put("user", user);
            return "index.xhtml";
		}
		return null;
	}
	
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login.xhtml";
    }
	
}
