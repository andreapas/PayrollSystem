package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.FullTimeController;
import it.unipv.payroll.controller.SessionManagementController;
import it.unipv.payroll.model.Credentials;
import it.unipv.payroll.model.FullTimeEmployee;

@Named
@ViewScoped
public class FullTimeBean implements Serializable {

	@Inject
	FullTimeController fullController;
	@Inject
	SessionManagementController smController;

	private FullTimeEmployee fullTimeEmployee;
	private FullTimeEmployee loggedUser;
	private List<FullTimeEmployee> fullTimersList;
	private Credentials newCred;

	@PostConstruct
	public void init() {
		fullTimeEmployee = new FullTimeEmployee();
		newCred = new Credentials();
		fullTimersList = fullController.findAll();
		try {
			loggedUser = fullController.find(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
		} catch (Exception e) {
			growl(FacesMessage.SEVERITY_FATAL, "Fatal Error", "Please Relog");
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

	// public void setFullTimersList(List<FullTimeEmployee> fullTimersList) {
	// this.fullTimersList = fullTimersList;
	// }

	public FullTimeEmployee getFullTimeEmployee() {
		return fullTimeEmployee;
	}

	public void setFullTimeEmployee(FullTimeEmployee fullTimeEmployee) {
		this.fullTimeEmployee = fullTimeEmployee;
	}

	public void hireEmployee() {
		try {
			fullController.add(fullTimeEmployee);
			smController.generateCredentials(fullTimeEmployee, newCred);
			growl(FacesMessage.SEVERITY_INFO, "Success!",
					"The employee " + fullTimeEmployee.getName() + " " + fullTimeEmployee.getSurname()
							+ " has been successfully hired",
					FacesMessage.SEVERITY_WARN, "Attention", "Password sent to the employee email. This password should be changed at first login.");

			// cancel();
			fullTimersList = fullController.findAll();
		} catch (Exception e) {
			growl(FacesMessage.SEVERITY_FATAL, "Error!",
					"Something has gone wrong while trying to hire " + fullTimeEmployee.getName() + " "
							+ fullTimeEmployee.getSurname() + ".\nThe complete message is " + e.getMessage());
		}
	}

	public void updateLoggedUser() {
		fullTimeEmployee = loggedUser;
		updateEmployee();
	}

	public void updateEmployee() {
		try {
			fullController.update(fullTimeEmployee);
			growl(FacesMessage.SEVERITY_INFO, "Success", "Your informations has been updated successfully.");

			fullTimersList = fullController.findAll();
		} catch (Exception e) {
			growl(FacesMessage.SEVERITY_FATAL, "Error!",
					"Something has gone wrong while trying to update your informations. The complete message is "
							+ e.getMessage());
		}
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
