package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

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
	private List<String> transList;
	private HashMap<String, Double> transMap;
	private List<Employee> employeeList;

	@PostConstruct
	public void init(){
		transList=new ArrayList<String>();
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
			
			transaction.setAmount((double)(transaction.getAmount()*employee.getCommissionRate()/100));
			transaction.setInfo("Sale ID="+id);
		}
		else {
			System.out.println("Impossible to add a sale recipt. You are not a monthly!");
		}
		String answer = tController.add(transaction);
		return answer;
	}
	
	public String addHours(PartTimeEmployee employee){
		
		transaction.setEmployee(employee);
		
		Date today = new Date();
		transaction.setDate(today);

		if (employee.getRole().equals("Weekly")) {
			
			if (hoursAmount>8) {
				transaction.setAmount((double)( 8*employee.getHourlyRate() + 1.5*(hoursAmount-8)*employee.getHourlyRate()));
			} else {
				transaction.setAmount((double)(hoursAmount*employee.getHourlyRate()));
			}
			transaction.setInfo("Worked hours");
		}
		else {
			System.out.println("Impossible to add hours. You are not a weekly!");
		}
		
		String answer = tController.add(transaction);
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
	public void addServiceCharge(){
		transaction.setDate(new Date());
		for (Employee employee : employeeList) {
			System.out.println("-----------------------------------------------------"+transaction.getAmount());
			transaction.setAmount(-transaction.getAmount());
			transaction.setEmployee(employee);
			tController.add(transaction);
		}
	}
	public HashMap<String, Double> startPayday() {
		HashMap<String, Double> employeesEarnings = tController.startPayday();
		List<String> tmpList=new ArrayList<String>();
		tmpList.addAll(employeesEarnings.keySet());
		transList=tmpList;
		transMap=employeesEarnings;
		return employeesEarnings;
	}
	public List<String> getTransList() {
		return transList;
	}
	public HashMap<String, Double> getTransMap() {
		return transMap;
	}
	public boolean isPayday() {
		// some logic
		return true;
	}
}
