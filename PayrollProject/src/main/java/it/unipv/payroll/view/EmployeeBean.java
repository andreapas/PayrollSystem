package it.unipv.payroll.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FlatEmployee;
import it.unipv.payroll.model.PartTimeEmployee;
import it.unipv.payroll.model.Union;

@Named
@SessionScoped
public class EmployeeBean implements Serializable{

	@Inject EmployeeController emController;
	
	private Employee partTimeEmployee;
	private Employee fullTimeEmployee;
	private Employee anEmployee;
	private Employee loggedUser;
	//private List<Employee> employeeList;
	
	@PostConstruct
	public void init(){
		loggedUser= emController.find( FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
		partTimeEmployee=new PartTimeEmployee();
		fullTimeEmployee=new FlatEmployee();
	}
	
	public Employee getLoggedUser() {
		return loggedUser;
	}
	public void setLoggedUser(Employee loggedUser) {
		this.loggedUser = loggedUser;
	}
	
//	public Employee getLoggedUser() {
//		anEmployee= emController.find( FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
//		return anEmployee;
//	}
//	public List<Employee> getEmployeeList() {
//		
//		return employeeList;
//	}

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

	public String hirePartTimeEmployee() {
		System.out.println("-------------------------------------------------------------------YEEEAAAAA");
		String answer=emController.add(partTimeEmployee);
		return answer;
	}

	
	
	
	
	public String fireEmployee() {
		String answer=emController.remove(anEmployee.getCode());
		return answer;
		
	}

	public String editEmail(String newEmail) {
		anEmployee.setEmail(newEmail);
		String answer=emController.update(anEmployee);
		return answer;
		
	}
	
	public String editUnion(Union newUnion) {
		anEmployee.setUnion(newUnion);
		String answer=emController.update(anEmployee);
		return answer;
		
	}

	public String editPaymentMethod(String newPaymentMethod) {
		anEmployee.setPayment_method(newPaymentMethod);
		String answer=emController.update(anEmployee);
		return answer;
	}

}
