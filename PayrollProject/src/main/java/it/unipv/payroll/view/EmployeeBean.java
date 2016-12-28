package it.unipv.payroll.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.Union;

@Named
@SessionScoped
public class EmployeeBean implements Serializable{

	@Inject EmployeeController emController;
	
	private Employee anEmployee;
	private Employee loggedUser;
	//private List<Employee> employeeList;
	
	@PostConstruct
	public void init(){
		loggedUser= emController.find( FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
	}
	
	public Employee getLoggedUser() {
		return loggedUser;
	}
	public void setLoggedUser(Employee loggedUser) {
		this.loggedUser = loggedUser;
	}
	public void setEmployee(Employee anEmployee) {
		this.anEmployee=anEmployee;
	}

	public Employee getEmployee() {
		return anEmployee;
	}
//	public Employee getLoggedUser() {
//		anEmployee= emController.find( FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
//		return anEmployee;
//	}
//	public List<Employee> getEmployeeList() {
//		
//		return employeeList;
//	}

	public String hireEmployee() {

		String answer=emController.add(anEmployee);
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
