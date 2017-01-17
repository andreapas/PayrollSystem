package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.PartTimeController;
import it.unipv.payroll.controller.SessionManagementController;
import it.unipv.payroll.controller.TransactionsController;
import it.unipv.payroll.model.Credentials;
import it.unipv.payroll.model.PartTimeEmployee;

@Named
@ViewScoped
@Stateful
public class PartTimeBean implements Serializable{

	@Inject
	PartTimeController partController;
	@Inject
	SessionManagementController smController;


	private PartTimeEmployee partTimeEmployee;
	private PartTimeEmployee loggedUser;
	private List<PartTimeEmployee> partTimersList;
	private Credentials newCred;


	
	@PostConstruct
	public void init() {
		newCred=new Credentials();
		partTimeEmployee = new PartTimeEmployee();
		System.out.println("Reconstructing...");
		try {
			loggedUser=partController.find(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
		} catch (Exception e) {
			System.out.println("Null face context detected. Bean run for testing");
		}
		partTimersList = partController.findAll();
	}
	public PartTimeEmployee getLoggedUser() {
		return loggedUser;
	}
	public void setLoggedUser(PartTimeEmployee loggedUser) {
		this.loggedUser = loggedUser;
	}
	public List<PartTimeEmployee> getPartTimersList() {
		return partTimersList;
	}
//	public void setPartTimersList(List<PartTimeEmployee> partTimersList) {
//		this.partTimersList = partTimersList;
//	}
	
	public PartTimeEmployee getPartTimeEmployee() {
		return partTimeEmployee;
	}
	
	public void setPartTimeEmployee(PartTimeEmployee partTimeEmployee) {
		System.out.println(partTimeEmployee.getCode());
		this.partTimeEmployee = partTimeEmployee;
	}
	
	public String hireEmployee() {
		partTimeEmployee.setRole("Weekly");
		if (partTimeEmployee.getPayment_method().equals("Postal Address")) {
			partTimeEmployee.setPayment_method(partTimeEmployee.getAddress().toString());
		}
		String answer = partController.add(partTimeEmployee);
		String tmpPassword = "";
		if (answer.equals("Operation completed successfully.")) {
			newCred.setCode(partTimeEmployee.getCode());
			smController.generateCredentials(newCred);
		}
		if (answer.equals("Operation completed successfully.")) {
			growl(FacesMessage.SEVERITY_INFO, "Success!",
					"The employee " + partTimeEmployee.getName() + " " + partTimeEmployee.getSurname()
							+ " has been successfully hired",
					FacesMessage.SEVERITY_WARN, "Attention", "Password: \'" + tmpPassword
							+ "\' without apices. This password should be changed at first login.");
		} else {
			growl(FacesMessage.SEVERITY_FATAL, "Error!",
					"Something has gone wrong while trying to hire " + partTimeEmployee.getName() + " "
							+ partTimeEmployee.getSurname() + ".\nThe complete message is " + answer);
			return answer;
		}
		// cancel();
		partTimersList = partController.findAll();
		return null;
	}
	public String updateLoggedUser(){
		partTimeEmployee=loggedUser;
		return updateEmployee();
	}
	public String updateEmployee() {
		if (partTimeEmployee.getPayment_method() != null) {
			if (partTimeEmployee.getPayment_method().equals("Postal address")) {
				partTimeEmployee.setPayment_method_details(partTimeEmployee.getAddress().toString());
			}  else if (partTimeEmployee.getPayment_method().equals("Paymaster")) {
				partTimeEmployee.setPayment_method_details("");
			}
		}
		String answer = partController.update(partTimeEmployee);
		if (answer.equals("Operation completed successfully.")) {
			growl(FacesMessage.SEVERITY_INFO, "Success", "Your informations has been updated successfully.");
		} else {
			growl(FacesMessage.SEVERITY_FATAL, "Error!",
					"Something has gone wrong while trying to update your informations. The complete message is "
							+ answer);
			return answer;
		}
		partTimersList=partController.findAll();
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
