package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.controller.FullTimeController;
import it.unipv.payroll.controller.TransactionsController;
import it.unipv.payroll.model.FullTimeEmployee;


@Named
@RequestScoped
@Stateful
public class FullTimeBean implements Serializable{

	@Inject
	FullTimeController fullController;
	@Inject
	EmployeeController emController;
	@Inject
	SessionManagementBean sessionManag;
	@Inject
	TransactionsController transController;

	private FullTimeEmployee fullTimeEmployee;
	private FullTimeEmployee loggedUser;
	private List<FullTimeEmployee> fullTimersList;

	
	
	@PostConstruct
	public void init() {
		fullTimeEmployee = new FullTimeEmployee();
		fullTimersList = fullController.findAll();
		try {
			loggedUser=fullController.find(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
		} catch (Exception e) {
			System.out.println("Null faces detected. This bean is in testing");
		}
	}

	public FullTimeEmployee getLoggedUser() {
		return loggedUser;
	}
	public void setLoggedUser(FullTimeEmployee loggedUser) {
		this.loggedUser = loggedUser;
	}
	public List<FullTimeEmployee> getFullTimersList() {
		return fullTimersList;
	}
	
//	public void setFullTimersList(List<FullTimeEmployee> fullTimersList) {
//		this.fullTimersList = fullTimersList;
//	}
	
	public FullTimeEmployee getFullTimeEmployee() {
		return fullTimeEmployee;
	}
	
	public void setFullTimeEmployee(FullTimeEmployee fullTimeEmployee) {
		this.fullTimeEmployee = fullTimeEmployee;
	}
	
	public String hireEmployee() {
		fullTimeEmployee.setRole("Monthly");
		if (fullTimeEmployee.getPayment_method().equals("Postal address")) {
			fullTimeEmployee.setPayment_method_details(fullTimeEmployee.getAddress());
		}

		String answer = fullController.add(fullTimeEmployee);
		String tmpPassword = "";
		if (answer.equals("Operation completed successfully.")) {
			sessionManag.setCode(fullTimeEmployee.getCode());
			tmpPassword = sessionManag.generatePassword();
			sessionManag.addLogin();
		}
		if (answer.equals("Operation completed successfully.")) {
			growl(FacesMessage.SEVERITY_INFO, "Success!",
					"The employee " + fullTimeEmployee.getName() + " " + fullTimeEmployee.getSurname()
							+ " has been successfully hired",
					FacesMessage.SEVERITY_WARN, "Attention", "Password: \'" + tmpPassword
							+ "\' without apices. This password should be changed at first login.");
		} else {
			growl(FacesMessage.SEVERITY_FATAL, "Error!",
					"Something has gone wrong while trying to hire " + fullTimeEmployee.getName() + " "
							+ fullTimeEmployee.getSurname() + ".\nThe complete message is " + answer);
			return answer;
		}
		// cancel();
		fullTimersList = fullController.findAll();
		return null;
	}
	public String updateLoggedUser(){
		fullTimeEmployee=loggedUser;
		return updateEmployee();
	}
	public String updateEmployee() {
		if (fullTimeEmployee.getPayment_method() != null) {
			if (fullTimeEmployee.getPayment_method().equals("Postal address")) {
				fullTimeEmployee.setPayment_method_details(fullTimeEmployee.getAddress());
			} else if (fullTimeEmployee.getPayment_method().equals("Paymaster")) {
				fullTimeEmployee.setPayment_method_details("");
			}
		}
		String answer = fullController.update(fullTimeEmployee);
		if (answer.equals("Operation completed successfully.")) {
			growl(FacesMessage.SEVERITY_INFO, "Success", "Your informations has been updated successfully.");
		} else {
			growl(FacesMessage.SEVERITY_FATAL, "Error!",
					"Something has gone wrong while trying to update your informations. The complete message is "
							+ answer);
			return answer;
		}
		fullTimersList=fullController.findAll();
		return null;
	}
	
	
	private void growl(Severity sevMessage, String title1, String message, Severity sevMessage2, String title2,
			String message2) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(sevMessage, title1, message));
			context.addMessage(null, new FacesMessage(sevMessage2, title2, message2));
		} catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}
	}

	private void growl(Severity sevMessage, String title, String message) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(sevMessage, title, message));
		} catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}
	}
}
