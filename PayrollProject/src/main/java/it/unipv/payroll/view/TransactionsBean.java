package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.controller.TransactionsController;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FullTimeEmployee;
import it.unipv.payroll.model.PartTimeEmployee;
import it.unipv.payroll.model.Transactions;

@Named
@SessionScoped
public class TransactionsBean implements Serializable {

	@Inject TransactionsController tController;

	private Transactions transaction;
	private int id;
//	private double saleAmount;
	private int hoursAmount;
	private List<Employee> employeeList;

	@PostConstruct
	public void init(){
		transaction = new Transactions();
		employeeList=new ArrayList<Employee>();
	}

	
	public List<Employee> getEmployeeList() {
		return employeeList;
	}


	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}


	public String addSaleRecipt(FullTimeEmployee employee) {
		
		transaction.setEmployee(employee);
		
		if (employee.getRole().equals("Monthly")) {
			
			transaction.setAmount((float)(transaction.getAmount()*employee.getCommissionRate()/100));
			transaction.setInfo("Sale ID="+id);
		}
		else {
			System.out.println("Impossible to add a sale recipt. You are not a monthly!");
		}
		String answer = tController.add(transaction);
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			if (answer.equals("Operation completed successfully.")) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "The sale receipt have been correctly added"));
			} else {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
								"Something has gone wrong while trying to add sale receipt. The complete message is " + answer));
			}
		} catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}
		return answer;
	}
	
	public String addHours(PartTimeEmployee employee){
		
		transaction.setEmployee(employee);
		
		Date today = new Date();
		transaction.setDate(today);

		if (employee.getRole().equals("Weekly")) {
			
			if (hoursAmount>8) {
				transaction.setAmount((float)( 8*employee.getHourlyRate() + 1.5*(hoursAmount-8)*employee.getHourlyRate()));
			} else {
				transaction.setAmount((float)(hoursAmount*employee.getHourlyRate()));
			}
			transaction.setInfo("Worked hours");
		}
		else {
			System.out.println("Impossible to add hours. You are not a weekly!");
		}
		
		String answer = tController.add(transaction);
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			if (answer.equals("Operation completed successfully.")) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "The worked hours have been correctly added"));
			} else {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
								"Something has gone wrong while trying to add the hours. The complete message is " + answer));
			}
		} catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}
		return answer;
	}

	public Transactions getTransaction() {
		return transaction;
	}
	public void setTransaction(Transactions transaction) {
		this.transaction = transaction;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

//	public double getSaleAmount() {
//		return saleAmount;
//	}
//
//	public void setSaleAmount(double saleAmount) {
//		this.saleAmount = saleAmount;
//	}

	public int getHoursAmount() {
		return hoursAmount;
	}

	public void setHoursAmount(int hoursAmount) {
		this.hoursAmount = hoursAmount;
	}
	
	public List<Transactions> getAllTransactions() {
		return tController.findAll();
	}
	@Inject EmployeeController emC;
	public void addServiceCharge(){
		String answer="";
		transaction.setDate(new Date());
		for (Employee employee : employeeList) {
			transaction.setAmount(-transaction.getAmount());
			transaction.setEmployee(emC.find(employee.getCode()));
			answer=tController.add(transaction);
		}
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			if (answer.equals("Operation completed successfully.")) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "The service charge has been successfully set"));
			} else {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
								"Something has gone wrong while trying to add the service charge. The complete message is " + answer));
			}
		} catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}
	}
//	public HashMap<String, Double> startPaydayForEmployeeType(String role) {
//		HashMap<String, Double> employeesEarnings = tController.startPaydayForEmployeeType(role);
//		return employeesEarnings;
//	}
}
