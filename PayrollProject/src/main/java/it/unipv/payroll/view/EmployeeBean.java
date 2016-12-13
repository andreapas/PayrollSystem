package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.model.Employee;

@Named
@SessionScoped
public class EmployeeBean implements Serializable{

	@Inject EmployeeController emController;
	
	private Employee anEmployee;
	//private List<Employee> employeeList;

	
	public void setEmployee(Employee anEmployee) {
		this.anEmployee=anEmployee;
	}

	public Employee getEmployee() {
		return anEmployee;
	}
	public Employee getLoggedUser() {
		anEmployee= emController.find( FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
		return anEmployee;
	}
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
	
	public String editUnion(String newUnion) {
		anEmployee.setUnion_Name(newUnion);
		String answer=emController.update(anEmployee);
		return answer;
		
	}

}
