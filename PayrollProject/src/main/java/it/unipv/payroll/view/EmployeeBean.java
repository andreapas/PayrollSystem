package it.unipv.payroll.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FullTimeEmployee;
import it.unipv.payroll.model.PartTimeEmployee;
import it.unipv.payroll.model.Union;

@Named
@SessionScoped
public class EmployeeBean implements Serializable {

	@Inject
	EmployeeController emController;

	private PartTimeEmployee partTimeEmployee;
	private FullTimeEmployee fullTimeEmployee;
	private Employee anEmployee;

	@PostConstruct
	public void init() {
		partTimeEmployee = new PartTimeEmployee();
		fullTimeEmployee = new FullTimeEmployee();
	}

	public void setAnEmployee(Employee anEmployee) {
		this.anEmployee = anEmployee;
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
	public String hireFullTimeEmployee() {
		fullTimeEmployee.setRole("Monthly");
		String answer = emController.add(fullTimeEmployee);
		return answer;

	}

	public String hirePartTimeEmployee() {
		partTimeEmployee.setRole("Weekly");
//		System.out.println(partTimeEmployee.getUnion().getUnionName());
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


	public Employee findEmployeeByCode(String code) {
		anEmployee=emController.find(code);
		return anEmployee;
	}

}
