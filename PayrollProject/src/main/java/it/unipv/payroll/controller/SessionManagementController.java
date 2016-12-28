package it.unipv.payroll.controller;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Credentials;

@Stateless
public class SessionManagementController extends GenericController<Credentials> {

	
	public boolean areValidCredential(String username, String password) {
		Credentials loginAttempt=dao.find(username);
		if(loginAttempt!=null){
			if (loginAttempt.getPassword().equals(password)) {
				return true;
			}else{
				return false;
			}
		} else{
			return false;
		}
	}
    
//	public String addLogin(Login login){
//		
//		loginDAO.add(login);
//		return SUCCESS;
//	}
//
//	public String removeLogin(Login login) {
//		loginDAO.remove(login.getUsername());
//		return SUCCESS;
//	}
//
//	public String updateLogin(Login login) {
//		loginDAO.update(login);
//		return SUCCESS;
//	}
//

	
}
