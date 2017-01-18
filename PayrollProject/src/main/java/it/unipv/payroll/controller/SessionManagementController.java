package it.unipv.payroll.controller;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Credentials;
import it.unipv.payroll.utils.PasswordManager;

@Stateless
public class SessionManagementController extends GenericController<Credentials> {

	private PasswordManager pswdManager = new PasswordManager();

	private boolean areValidCredential(String username, String password) {
		Credentials loginAttempt = dao.find(username);
		if (loginAttempt != null) {
			// System.out.println(loginAttempt.getPassword());
			// System.out.println(pswdManager.hashIt(password));
			if (loginAttempt.getPassword().equals(pswdManager.hashIt(password))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void generateCredentials(Credentials credentials) throws Exception {
		String password = pswdManager.generatePassword();
		credentials.setPassword(pswdManager.hashIt(password));
		super.add(credentials);
	}

	@Override
	public Credentials find(Object id) throws Exception {
		String pk=(String)id;
		if (pk == null || pk.isEmpty())
			throw new Exception("Cannot find null or empty id. " + ERROR);
		return dao.find(id);
	}
	
	
	public void changePassword(Credentials oldest,Credentials newest) throws Exception {
		if(!areValidCredential(oldest.getCode(), oldest.getPassword()))
			throw new Exception("Wrong Password inserted. Please, try again");
		newest.setPassword(pswdManager.hashIt(newest.getPassword()));
		super.update(newest);
	}

	@Override
	public boolean isAlreadyInDatabase(Credentials element) {
		Credentials login = dao.find(element.getCode());
		if (login != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isElementOk(Credentials element) {
		if (element.getCode().isEmpty() || element.getCode() == null)
			return false;
		if (element.getPassword().isEmpty() || element.getPassword() == null)
			return false;
		return true;
	}

}
