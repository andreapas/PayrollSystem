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
public class EmployeeBean implements Serializable {

	@Inject
	EmployeeController emController;

	private Employee partTimeEmployee;
	private Employee fullTimeEmployee;
	private Employee anEmployee;

	@PostConstruct
	public void init() {
		partTimeEmployee = new PartTimeEmployee();
		fullTimeEmployee = new FlatEmployee();
	}

	public void setAnEmployee(Employee anEmployee) {
		this.anEmployee = anEmployee;
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

	public String hirePartTimeEmployee() {
		String answer = emController.add(partTimeEmployee);
		return answer;
	}

	public String fireEmployee(Employee employee) {
		String answer = emController.remove(employee.getCode());
		return answer;

	}

	public String editEmail(String newEmail) {
		anEmployee.setEmail(newEmail);
		String answer = emController.update(anEmployee);
		return answer;

	}

	public String editUnion(Union newUnion) {
		anEmployee.setUnion(newUnion);
		String answer = emController.update(anEmployee);
		return answer;

	}

	public String editPaymentMethod(String newPaymentMethod) {
		anEmployee.setPayment_method(newPaymentMethod);
		String answer = emController.update(anEmployee);
		return answer;
	}

	public String hireFullTimeEmployee() {
		String answer = emController.add(fullTimeEmployee);
		return answer;

	}

	public Employee findEmployeeByCode(String code) {
		anEmployee=emController.find(code);
		return anEmployee;
	}

}
