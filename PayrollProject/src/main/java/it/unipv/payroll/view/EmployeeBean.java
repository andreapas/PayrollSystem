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
import it.unipv.payroll.controller.TransactionsController;
import it.unipv.payroll.model.Employee;

@Named
@RequestScoped
@Stateful
public class EmployeeBean implements Serializable {

	@Inject
	EmployeeController emController;
	@Inject
	SessionManagementBean sessionManag;
	@Inject
	TransactionsController transController;

	private static String paymaster = "Paymaster";
	private static String postal_address = "Postal address";
	private static String bank_account = "Bank account";
	private List<String> optionsList = new ArrayList<String>(Arrays.asList(paymaster, postal_address, bank_account));
	private List<Employee> employeeList;

	@PostConstruct
	public void init() {
		employeeList = emController.findAll();
	}

	public List<Employee> getEmployeeList() {
		employeeList = emController.findAll();
		return employeeList;
	}

//	public void setEmployeeList(List<Employee> employeeList) {
//		this.employeeList = employeeList;
//	}

	public List<String> getOptionsList() {
		return optionsList;
	}

	public String fireEmployee(String fireCode) {
		String answer = emController.remove(fireCode);
		if (answer.equals("Operation completed successfully.")) {
			growl(FacesMessage.SEVERITY_INFO, "Success!", "The employee with code:" + fireCode + " has been successfully fired");
			sessionManag.removeLogin(fireCode);

		} else {
			growl(FacesMessage.SEVERITY_FATAL, "Error!", "Something has gone wrong while trying to fire employee with code: "+fireCode+". The complete message is " + answer);
			return answer;
		}
		employeeList = emController.findAll();
		return null;
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
