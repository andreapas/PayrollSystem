package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.dao.EmployeeDAO;
import it.unipv.payroll.model.Employee;

@Named
@SessionScoped
public class EmployeeBean implements Serializable{

	@Inject EmployeeController emController;
	
	private Employee anEmployee;
	private List<Employee> employeeList;

	
	public void setEmployee(Employee anEmployee) {
		this.anEmployee=anEmployee;
	}

	public Employee getEmployee() {
		return anEmployee;
	}
	
	public List<Employee> getEmployeeList() {
		
		return employeeList;
	}

	public void hireEmployee() {
		try {
			employeeList=emController.hireEmployee(anEmployee);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void fireEmployee() {
		employeeList=emController.fireEmployee(anEmployee);
		
	}

	public void editEmail(String newEmail) {
		anEmployee.setEmail(newEmail);
		employeeList=emController.editEmail(anEmployee);
		
	}
	
	public void editUnion(String newUnion) {
		anEmployee.setUnion_Name(newUnion);
		employeeList=emController.editUnion(anEmployee);
		
	}

}
