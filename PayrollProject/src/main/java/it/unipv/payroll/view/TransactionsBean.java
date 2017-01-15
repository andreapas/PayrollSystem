package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
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
@RequestScoped
public class TransactionsBean implements Serializable {

	@Inject TransactionsController tController;
	@Inject	EmployeeController emController;

	private Transactions transaction;
	private int id;
//	private double saleAmount;
	private List<Transactions> transactionList;
	private int hoursAmount;
	private Employee loggedEmployee;
	
	@PostConstruct
	public void init(){
		transaction = new Transactions();
		transactionList= new ArrayList<Transactions>();
	}

	public void setLoggedEmployee(Employee loggedEmployee) {
		this.loggedEmployee = loggedEmployee;
	}

	

	public void addSaleRecipt() {
		try {
			loggedEmployee=emController.find(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
		} catch (NullPointerException e) {
			System.out.println("Null context detected. Maybe this bean as been run for testing");
		}
		transaction.setEmployee(loggedEmployee);
		
		if (loggedEmployee.getRole().equals("Monthly")) {
			
			transaction.setAmount((float)(transaction.getAmount()*((FullTimeEmployee)loggedEmployee).getCommissionRate()/100));
			transaction.setInfo("Sale ID="+id);
		}else {
			System.out.println("Impossible to add a sale recipt. You are not a Full time employee!");
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
		transactionList=tController.findAll();

	}
	
	public void addHours(){
		try {
			loggedEmployee=emController.find(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
		} catch (NullPointerException e) {
			System.out.println("Null context detected. Maybe this bean as been run for testing");
		}
		transaction.setEmployee(loggedEmployee);
		
		Date today = new Date();
		transaction.setDate(today);

		if (loggedEmployee.getRole().equals("Weekly")) {
			float rate=((PartTimeEmployee)loggedEmployee).getHourlyRate();
			if (hoursAmount>8) {
				transaction.setAmount((float)( 8*rate + 1.5*(hoursAmount-8)*rate));
			} else {
				transaction.setAmount((float)(hoursAmount*rate));
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
		transactionList=tController.findAll();
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
		return transactionList;
	}
	public void addServiceCharge(String employeeCode){
		String answer="";
		transaction.setDate(new Date());
		transaction.setAmount(-transaction.getAmount());
		transaction.setEmployee(emController.find(employeeCode));
		answer=tController.add(transaction);
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
		transactionList=tController.findAll();
	}
//	public HashMap<String, Double> startPaydayForEmployeeType(String role) {
//		HashMap<String, Double> employeesEarnings = tController.startPaydayForEmployeeType(role);
//		return employeesEarnings;
//	}
}
