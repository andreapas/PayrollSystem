package it.unipv.payroll.view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.LoginController;
import it.unipv.payroll.model.Login;

@Named
@SessionScoped
public class LoginBean implements Serializable{

	@Inject LoginController logController;
	
	
	private Login aLogin;
	private String answer;
	
	public Login getLogin() {
		return aLogin;
	}
	public void setLogin(Login aLogin) {
		this.aLogin = aLogin;
	}

	public String addLogin() {
		answer=logController.addLogin(aLogin);
		return answer;
	}
	
	public String removeLogin(){
		answer=logController.removeLogin(aLogin);
		return answer;
	}
	
	public String updateLogin(String newPassword){
		aLogin.setHashPassword(newPassword);
		answer=logController.updateLogin(aLogin);
		return answer;
	}
	public String login() {
		answer=logController.login(aLogin);
		return answer;
	}
}
