package it.unipv.payroll.controller;

import javax.ejb.Stateless;
import javax.inject.Inject;

import it.unipv.payroll.dao.LoginDAO;
import it.unipv.payroll.model.Login;

@Stateless
public class LoginController {

	@Inject LoginDAO logDAO;
	
	private static String SUCCESS="Success";
	private static String WRONG="Wrong Username or Password";
	
	
	public String addLogin(Login aLogin){
		
		logDAO.add(aLogin);
		
		return SUCCESS;
	}


	public String removeLogin(Login aLogin) {
		logDAO.remove(aLogin);
		return SUCCESS;
	}


	public String updateLogin(Login aLogin) {
		logDAO.update(aLogin);
		return SUCCESS;
	}


	public String login(Login aLogin) {
		Login foundCredentials=logDAO.getLoginCredentials(aLogin.getHashUsername());
		if(foundCredentials==null){
			return WRONG;
		}else if(foundCredentials.getHashPassword().equals(aLogin.getHashPassword())){
			return SUCCESS;
		}
		return WRONG;
	}
	
}
