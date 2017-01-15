package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import it.unipv.payroll.controller.AddressController;
import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.controller.TransactionsController;
import it.unipv.payroll.model.Address;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FullTimeEmployee;
import it.unipv.payroll.model.PartTimeEmployee;
import it.unipv.payroll.model.Transactions;

@Named
@SessionScoped
@Stateful
public class EmployeeBean implements Serializable {

	@Inject
	EmployeeController emController;
	@Inject
	SessionManagementBean sessionManag;
	@Inject
	AddressController addrController;
	@Inject
	TransactionsController transController;

	private Employee partTimeEmployee;
	private Employee fullTimeEmployee;
	private Employee loggedUser;
	private List<Employee> employeeList;
	// private String fireCode;
	private String radioVal;
	private Employee genericEmployee;
	private List<String> districtCodeList;
	private Address address;
	private List<String> optionsList;
	private static String paymaster = "Paymaster";
	private static String postal_address = "Postal address";
	private static String bank_account = "Bank account";

	@PostConstruct
	public void init() {
		partTimeEmployee = new PartTimeEmployee();
		fullTimeEmployee = new FullTimeEmployee();

		try {
			loggedUser = emController.find(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
		} catch (NullPointerException e) {
			System.out.println("Testing phase detected, null context face returned");
		}
		employeeList = emController.findAll();
		radioVal = new String();
		districtCodeList = new ArrayList<String>(Arrays.asList("AG", "AL", "AN", "AO", "AQ", "AR", "AP", "AT", "AV",
				"BA", "BT", "BL", "BN", "BG", "BI", "BO", "BZ", "BS", "BR", "CA", "CL", "CB", "CI", "CE", "CT", "CZ",
				"CH", "CO", "CS", "CR", "KR", "CN", "EN", "FM", "FE", "FI", "FG", "FC", "FR", "GE", "GO", "GR", "IM",
				"IS", "SP", "LT", "LE", "LC", "LI", "LO", "LU", "MC", "MN", "MS", "MT", "VS", "ME", "MI", "MO", "MB",
				"NA", "NO", "NU", "OG", "OT", "OR", "PD", "PA", "PR", "PV", "PG", "PU", "PE", "PC", "PI", "PT", "PN",
				"PZ", "PO", "RG", "RA", "RC", "RE", "RI", "RN", "RM", "RO", "SA", "SS", "SV", "SI", "SR", "SO", "TA",
				"TE", "TR", "TO", "TP", "TN", "TV", "TS", "UD", "VA", "VE", "VB", "VC", "VR", "VV", "VI"));
		address = new Address();
		optionsList = new ArrayList<String>(Arrays.asList(paymaster, postal_address, bank_account));
	}

	// public String getFireCode() {
	// return fireCode;
	// }

	// public void setFireCode(String fireCode) {
	// this.fireCode = fireCode;
	// }

	public Employee getLoggedUser() {
		return loggedUser;
	}

	public Employee getPartTimeEmployee() {
		return partTimeEmployee;
	}

	public void setPartTimeEmployee(Employee partTimeEmployee) {
		this.partTimeEmployee = partTimeEmployee;
	}

	public Employee getFullTimeEmployee() {
		return fullTimeEmployee;
	}

	public void setFullTimeEmployee(Employee fullTimeEmployee) {
		this.fullTimeEmployee = fullTimeEmployee;
	}

	public void hirePartTimeEmployee() {
		address.setCode(partTimeEmployee.getCode());
		partTimeEmployee.setRole("Weekly");
		partTimeEmployee.setAddress(address);
		if (radioVal.equals(postal_address)) {
			partTimeEmployee.setPayment_method(partTimeEmployee.getAddress().toString());
		} else if (radioVal.equals(paymaster)) {
			partTimeEmployee.setPayment_method(paymaster);
		}
		String answer = emController.add(partTimeEmployee);
		String tmpPassword = "";
		if (answer.equals("Operation completed successfully.")) {
			sessionManag.setCode(partTimeEmployee.getCode());
			tmpPassword = sessionManag.generatePassword();
			sessionManag.addLogin();
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
		}
		// cancel();
		employeeList = emController.findAll();
	}

	public void hireFullTimeEmployee() {
		address.setCode(fullTimeEmployee.getCode());
		fullTimeEmployee.setRole("Monthly");
		fullTimeEmployee.setAddress(address);
		if (radioVal.equals(postal_address)) {
			fullTimeEmployee.setPayment_method(fullTimeEmployee.getAddress().toString());
		} else if (radioVal.equals(paymaster)) {
			fullTimeEmployee.setPayment_method(paymaster);
		}

		String answer = emController.add(fullTimeEmployee);
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
		}
		// cancel();
		employeeList = emController.findAll();
	}

	public void fireEmployee(String fireCode) {
		Employee employee = findEmployeeByCode(fireCode);
		String answer;
		if (!employee.getRole().equals("Manager")) {
			sessionManag.removeLogin(employee.getCode());
//			List<Transactions> tList = transController.findAll();
//			for (Transactions transactions : tList) {
//				if (transactions.getEmployee().getCode().equals(employee.getCode())) {
//					transController.remove(transactions.getId());
//				}
//			}
			answer = emController.remove(employee.getCode());
			addrController.remove(employee.getCode());

		} else {
			answer = "You don't have the permission to remove a Manager";
		}
		if (answer.equals("Operation completed successfully.")) {
			growl(FacesMessage.SEVERITY_INFO, "Success!", "The employee " + employee.getName() + " "
					+ employee.getSurname() + " has been successfully fired");
		} else {
			growl(FacesMessage.SEVERITY_FATAL, "Error!", "Something has gone wrong while trying to fire "
					+ employee.getName() + " " + employee.getSurname() + ". The complete message is " + answer);
		}
		employeeList = emController.findAll();
	}

	public void updateInfo() {
		// System.out.println(">>>><<<<<>>>>>>><<<<<<<<<>>>>>>>><<<<<<<<<<<<<<>>>>>>>>>"+loggedUser.getPayment_method());
		if (radioVal != null) {
			if (radioVal.equals(postal_address)) {
				genericEmployee.setPayment_method(genericEmployee.getAddress().toString());
			} else if (radioVal.equals(paymaster)) {
				genericEmployee.setPayment_method(paymaster);
			}
		}
		addrController.update(genericEmployee.getAddress());
		String answer = emController.update(genericEmployee);
		if (answer.equals("Operation completed successfully.")) {
			growl(FacesMessage.SEVERITY_INFO, "Success", "Your informations has been updated successfully.");
		} else {
			growl(FacesMessage.SEVERITY_FATAL, "Error!",
					"Something has gone wrong while trying to update your informations. The complete message is "
							+ answer);
		}
		employeeList=emController.findAll();
	}

	public Employee findEmployeeByCode(String code) {
		return emController.find(code);
	}

	// TODO: to be tested
	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	public String getRadioVal() {
		return radioVal;
	}

	public void setRadioVal(String radioVal) {
		this.radioVal = radioVal;
	}

	public Employee getGenericEmployee() {
		return genericEmployee;
	}

	public void setGenericEmployee(Employee genericEmployee) {
		this.genericEmployee = genericEmployee;
	}

	public List<String> getOptionsList() {
		return optionsList;
	}

	public List<String> getDistrictCodeList() {
		return districtCodeList;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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
