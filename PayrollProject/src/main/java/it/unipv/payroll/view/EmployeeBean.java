package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import it.unipv.payroll.controller.AddressController;
import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.model.Address;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FullTimeEmployee;
import it.unipv.payroll.model.PartTimeEmployee;

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

	private PartTimeEmployee partTimeEmployee;
	private FullTimeEmployee fullTimeEmployee;
	private Employee loggedUser;
	private List<Employee> employeeList;
	private String fireCode;
	private String radioVal;
	private Employee genericEmployee;
	private List<String> districtCodeList;
	private Address address;

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
	}

	public String getFireCode() {
		return fireCode;
	}

	public void setFireCode(String fireCode) {
		this.fireCode = fireCode;
	}

	public Employee getLoggedUser() {
		return loggedUser;
	}

	public PartTimeEmployee getPartTimeEmployee() {
		return partTimeEmployee;
	}

	public void setPartTimeEmployee(PartTimeEmployee partTimeEmployee) {
		this.partTimeEmployee = partTimeEmployee;
	}

	public FullTimeEmployee getFullTimeEmployee() {
		return fullTimeEmployee;
	}

	public void setFullTimeEmployee(FullTimeEmployee fullTimeEmployee) {
		this.fullTimeEmployee = fullTimeEmployee;
	}

	public void hirePartTimeEmployee() {
		partTimeEmployee.setRole("Weekly");
		address.setCode(partTimeEmployee.getCode());
		partTimeEmployee.setAddress(address);
		if (radioVal.equals("Postal address")) {
			partTimeEmployee.setPayment_method(partTimeEmployee.getAddress().toString());
		} else if (radioVal.equals("Paymaster")) {
			partTimeEmployee.setPayment_method("Paymaster");
		}
		String answer = emController.add(partTimeEmployee);
		String tmpPassword = "";
		if (answer.equals("Operation completed successfully.")) {
			sessionManag.setCode(partTimeEmployee.getCode());
			tmpPassword = sessionManag.generatePassword();
			sessionManag.addLogin();
		}
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			if (answer.equals("Operation completed successfully.")) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
								"The employee " + partTimeEmployee.getName() + " " + partTimeEmployee.getSurname()
										+ " has been successfully hired"));
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Password: \'"
						+ tmpPassword + "\' without apices. This password should be changed at first login."));
			} else {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
								"Something has gone wrong while trying to hire " + partTimeEmployee.getName() + " "
										+ partTimeEmployee.getSurname() + ".\nThe complete message is " + answer));
			}
		} catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}
		cancel();
		employeeList = emController.findAll();
	}

	private void cancel(){
		partTimeEmployee=new PartTimeEmployee();
		fullTimeEmployee=new FullTimeEmployee();
		address=new Address();
		fireCode="";
	}
	public void hireFullTimeEmployee() {
		fullTimeEmployee.setRole("Monthly");
		address.setCode(fullTimeEmployee.getCode());
		fullTimeEmployee.setAddress(address);
		if (radioVal.equals("Postal address")) {
			fullTimeEmployee.setPayment_method(fullTimeEmployee.getAddress().toString());
		} else if (radioVal.equals("Paymaster")) {
			fullTimeEmployee.setPayment_method("Paymaster");
		}

		String answer = emController.add(fullTimeEmployee);
		String tmpPassword = "";
		if (answer.equals("Operation completed successfully.")) {
			sessionManag.setCode(fullTimeEmployee.getCode());
			tmpPassword = sessionManag.generatePassword();
			sessionManag.addLogin();
		}
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			if (answer.equals("Operation completed successfully.")) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
								"The employee " + fullTimeEmployee.getName() + " " + fullTimeEmployee.getSurname()
										+ " has been successfully hired"));
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Password: \'"
						+ tmpPassword + "\' without apices. This password should be changed at first login."));

			} else {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
								"Something has gone wrong while trying to hire " + fullTimeEmployee.getName() + " "
										+ fullTimeEmployee.getSurname() + ".\nThe complete message is " + answer));
			}
		} catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}
		cancel();
		employeeList = emController.findAll();
	}

	public List<Employee> fireEmployee() {
		Employee employee = findEmployeeByCode(fireCode);
		String answer = emController.remove(employee.getCode());
		sessionManag.removeLogin(employee.getCode());
		addrController.remove(employee.getCode());
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			if (answer.equals("Operation completed successfully.")) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "The employee "
						+ employee.getName() + " " + employee.getSurname() + " has been successfully fired"));
			} else {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
								"Something has gone wrong while trying to fire " + employee.getName() + " "
										+ employee.getSurname() + ". The complete message is " + answer));
			}
		} catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}
		employeeList = emController.findAll();
		return employeeList;

	}

	public void updateInfo(Employee employee) {
		// System.out.println(">>>><<<<<>>>>>>><<<<<<<<<>>>>>>>><<<<<<<<<<<<<<>>>>>>>>>"+loggedUser.getPayment_method());
		if (radioVal != null) {
			if (radioVal.equals("Postal address")) {
				employee.setPayment_method(employee.getAddress().toString());
			} else if (radioVal.equals("Paymaster")) {
				employee.setPayment_method("Paymaster");
			}
		}
		String answer = emController.update(employee);
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			if (answer.equals("Operation completed successfully.")) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
						"Your informations has been updated successfully."));
			} else {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
								"Something has gone wrong while trying to update your informations. The complete message is "
										+ answer));
			}
		} catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}
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

//	public void cancel() {
//		genericEmployee = null;
//		fireCode = "";
//		partTimeEmployee = new PartTimeEmployee();
//		fullTimeEmployee = new FullTimeEmployee();
//		address = new Address();
//
//	}

//	public String updateGenericEmployee() {
//		// System.out.println(">>>><<<<<>>>>>>><<<<<<<<<>>>>>>>><<<<<<<<<<<<<<>>>>>>>>>"+loggedUser.getPayment_method());
//
//		// if (radioVal.equals("Postal address")) {
//		// loggedUser.setPayment_method(address.toString());
//		// } else if (radioVal.equals("Paymaster")) {
//		// loggedUser.setPayment_method("Paymaster");
//		// }
//		String answer = emController.update(genericEmployee);
//
//		try {
//			FacesContext context = FacesContext.getCurrentInstance();
//			if (answer.equals("Operation completed successfully.")) {
//				context.addMessage(null,
//						new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
//								"Employee " + genericEmployee.getName() + " " + genericEmployee.getSurname()
//										+ "'s informations has been successfully updated."));
//			} else {
//				context.addMessage(null,
//						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
//								"Something has gone wrong while trying to update the informations of "
//										+ genericEmployee.getName() + " " + genericEmployee.getSurname()
//										+ ". The complete message is " + answer));
//			}
//		} catch (NullPointerException e) {
//			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
//		}
//
//		// TODO:CLEAR TUTTO
//		return answer;
//	}

	public List<String> getDistrictCodeList() {
		return districtCodeList;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
