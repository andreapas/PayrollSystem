package it.unipv.payroll.controller;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Credentials;
import it.unipv.payroll.utils.PasswordManager;

@Stateless
public class SessionManagementController extends GenericController<Credentials> {

	private PasswordManager pswdManager= new PasswordManager();
	
	public boolean areValidCredential(String username, String password) {
		Credentials loginAttempt=dao.find(username);
		if(loginAttempt!=null){
//			System.out.println(loginAttempt.getPassword());
//			System.out.println(pswdManager.hashIt(password));
			if (loginAttempt.getPassword().equals(pswdManager.hashIt(password))) {
				return true;
			}else{
				return false;
			}
		} else{
			return false;
		}
	}

	public String generatePassword() {
		return pswdManager.generatePassword();
	}
    
	@Override
	public String add(Credentials element) {
		element.setPassword(pswdManager.hashIt(element.getPassword()));
		return super.add(element);
	}
	
	@Override
	public String update(Credentials element) {
		element.setPassword(pswdManager.hashIt(element.getPassword()));
		return super.update(element);
	}
	
	@Override
	public boolean isAlreadyInDatabase(Credentials element) {
		Credentials login= dao.find(element.getCode());
		if(login!=null){
			return true;
		}
		return false;
	}

	
}
