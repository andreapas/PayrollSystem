package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
	@Inject
	SessionManagementBean sessionManag;

	private PartTimeEmployee partTimeEmployee;
	private FullTimeEmployee fullTimeEmployee;
	private Employee anEmployee;
	private List<Employee> employeeList;

	@PostConstruct
	public void init() {
		partTimeEmployee = new PartTimeEmployee();
		fullTimeEmployee = new FullTimeEmployee();
		employeeList=emController.findAll();
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
	public String hirePartTimeEmployee() {
		partTimeEmployee.setRole("Weekly");
		String answer = emController.add(partTimeEmployee);
		String tmpPassword="";
		if (answer.equals("Operation completed successfully.")) {
			sessionManag.setCode(partTimeEmployee.getCode());
			tmpPassword=sessionManag.generatePassword();
			sessionManag.addLogin();
		}
		try {
			FacesContext context = FacesContext.getCurrentInstance();
	        if(answer.equals("Operation completed successfully.")){
		        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success!",  "The employee "+partTimeEmployee.getName()+" "+partTimeEmployee.getSurname()+" has been successfully hired") );
		        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Attention",  "Password: \'"+tmpPassword+"\' without apices. This password should be changed at first login.") );
	        }else {
	        	context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Error!",  "Something has gone wrong while trying to hire "+partTimeEmployee.getName()+" "+partTimeEmployee.getSurname()+".\nThe complete message is "+answer) );
			}
		} catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}
		employeeList=emController.findAll();
		return null;
	}
	
	
	public String hireFullTimeEmployee() {
		fullTimeEmployee.setRole("Monthly");
		String answer = emController.add(fullTimeEmployee);
		String tmpPassword="";
		if (answer.equals("Operation completed successfully.")) {
			sessionManag.setCode(fullTimeEmployee.getCode());
			tmpPassword=sessionManag.generatePassword();
			sessionManag.addLogin();
		}
		try {
			FacesContext context = FacesContext.getCurrentInstance();
	        if(answer.equals("Operation completed successfully.")){
		        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success!",  "The employee "+fullTimeEmployee.getName()+" "+fullTimeEmployee.getSurname()+" has been successfully hired") );
		        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Attention",  "Password: \'"+tmpPassword+"\' without apices. This password should be changed at first login.") );

	        }else {
	        	context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Error!",  "Something has gone wrong while trying to hire "+fullTimeEmployee.getName()+" "+fullTimeEmployee.getSurname()+".\nThe complete message is "+answer) );
			}
		} catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}
		
		employeeList=emController.findAll();

		
		return null;

	}

	

	public String fireEmployee(Employee employee) {
		String answer = emController.remove(employee.getCode());
		sessionManag.removeLogin(employee.getCode());
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

	//TODO: to be tested
	public List<Employee> getEmployeeList(){
		employeeList= emController.findAll();
		return employeeList;
	}
	
}
