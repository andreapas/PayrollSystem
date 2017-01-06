package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FullTimeEmployee;
import it.unipv.payroll.model.PartTimeEmployee;
import it.unipv.payroll.model.Union;
import it.unipv.payroll.utils.Address;

@Named
@SessionScoped
@Stateful
public class EmployeeBean implements Serializable {

	@Inject
	EmployeeController emController;
	@Inject
	SessionManagementBean sessionManag;

	private PartTimeEmployee partTimeEmployee;
	private FullTimeEmployee fullTimeEmployee;
	private Employee loggedUser;
	private List<Employee> employeeList;
	private String fireCode;
	private String radioVal;
	private Address address;
	
	
	@PostConstruct
	public void init() {
		partTimeEmployee = new PartTimeEmployee();
		fullTimeEmployee = new FullTimeEmployee();
		try{
			loggedUser = emController.find(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
		}catch (NullPointerException e) {
			System.out.println("Testing phase detected, null context face returned");
		}
		employeeList=emController.findAll();
		address=new Address();
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
	
	public void setLoggedUser(Employee anEmployee) {
		loggedUser = anEmployee;
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
	
	public List<Employee> hirePartTimeEmployee() {
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
		return employeeList;
	}
	
	
	public List<Employee> hireFullTimeEmployee() {
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

		
		return employeeList;

	}

	

	public List<Employee> fireEmployee() {
		Employee employee=findEmployeeByCode(fireCode);
		String answer = emController.remove(employee.getCode());
		sessionManag.removeLogin(employee.getCode());
		try {
			FacesContext context = FacesContext.getCurrentInstance();
	        if(answer.equals("Operation completed successfully.")){
		        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success!",  "The employee "+employee.getName()+" "+employee.getSurname()+" has been successfully fired") );
	        }else {
	        	context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Error!",  "Something has gone wrong while trying to fire "+employee.getName()+" "+employee.getSurname()+". The complete message is "+answer) );
			}
		} catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}
		fireCode="";
		employeeList=emController.findAll();
		return employeeList;

	}


	public String updateInfo() {
//		System.out.println(">>>><<<<<>>>>>>><<<<<<<<<>>>>>>>><<<<<<<<<<<<<<>>>>>>>>>"+loggedUser.getPayment_method());
		
		if(radioVal.equals("Postal address")){
			loggedUser.setPayment_method(address.toString());
		}else if (radioVal.equals("Paymaster")) {
			loggedUser.setPayment_method("Paymaster");
		}
		clearAddress();
		String answer = emController.update(loggedUser);		
		try {
			FacesContext context = FacesContext.getCurrentInstance();
	        if(answer.equals("Operation completed successfully.")){
		        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success!",  "Your informations has been updated successfully."));
	        }else {
	        	context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Error!",  "Something has gone wrong while trying to update your informations. The complete message is "+answer) );
			}
		} catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}
		return answer;
	}

	private void clearAddress(){
		address.setStreet("");
		address.setCap(0);
		address.setDistrictCode("");
		address.setMunicipality("");
		address.setNumber(0);
	}
	
	public Employee findEmployeeByCode(String code) {
		return emController.find(code);
	}

	//TODO: to be tested
	public List<Employee> getEmployeeList(){
		return employeeList;
	}
	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}
	public void onRowEdit(RowEditEvent event) {
		Employee employee=((Employee)event.getObject());
		String answer=emController.update(employee);
		try {
			FacesContext context = FacesContext.getCurrentInstance();
	        if(answer.equals("Operation completed successfully.")){
		        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success!",  "Employee "+employee.getName()+" "+ employee.getSurname()+" updated successfully!") );
	        }else {
	        	context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Error!",  "Something has gone wrong while trying to update employee "+employee.getName()+" "+ employee.getSurname()+". The complete message is "+answer) );
			}
		} catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}
    }
     
    public void onRowCancel(RowEditEvent event) {
		Employee employee=((Employee)event.getObject());
        try {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Edit Cancelled", "Employee "+employee.getName()+" "+employee.getSurname()+" has not been edited.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}
    }

    public String getRadioVal() {
		return radioVal;
	}

	public void setRadioVal(String radioVal) {
		this.radioVal = radioVal;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
    
}
