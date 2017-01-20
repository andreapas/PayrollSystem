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

import it.unipv.payroll.controller.PartTimeController;
import it.unipv.payroll.controller.SessionManagementController;
import it.unipv.payroll.model.Credentials;
import it.unipv.payroll.model.PartTimeEmployee;

@Named
@ViewScoped
public class PartTimeBean implements Serializable {

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
		newCred = new Credentials();
		partTimeEmployee = new PartTimeEmployee();
		try {
			loggedUser = partController.find(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
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
	// public void setPartTimersList(List<PartTimeEmployee> partTimersList) {
	// this.partTimersList = partTimersList;
	// }

	public PartTimeEmployee getPartTimeEmployee() {
		return partTimeEmployee;
	}

	public void setPartTimeEmployee(PartTimeEmployee partTimeEmployee) {
		System.out.println(partTimeEmployee.getCode());
		this.partTimeEmployee = partTimeEmployee;
	}

	public void hireEmployee() {
		try {
			partController.add(partTimeEmployee);
			newCred.setCode(partTimeEmployee.getCode());
			smController.generateCredentials(partTimeEmployee,newCred);
			growl(FacesMessage.SEVERITY_INFO, "Success!",
					"The employee " + partTimeEmployee.getName() + " " + partTimeEmployee.getSurname()
							+ " has been successfully hired",
					FacesMessage.SEVERITY_WARN, "Attention",
					"Password sent to the employee email. This password should be changed at first login.");
			partTimersList = partController.findAll();
		} catch (Exception e) {
			growl(FacesMessage.SEVERITY_FATAL, "Error!",
					"Something has gone wrong while trying to hire " + partTimeEmployee.getName() + " "
							+ partTimeEmployee.getSurname() + ".\nThe complete message is " + e.getMessage());

		}
	}

	public void updateLoggedUser() {
		partTimeEmployee = loggedUser;
		updateEmployee();
	}

	public void updateEmployee() {
		try {
			partController.update(partTimeEmployee);
			growl(FacesMessage.SEVERITY_INFO, "Success", "Your informations has been updated successfully.");
			partTimersList = partController.findAll();
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
