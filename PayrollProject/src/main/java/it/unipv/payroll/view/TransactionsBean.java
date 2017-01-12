package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.plaf.synth.SynthSeparatorUI;

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
	private double saleAmount;
	private int hoursAmount;

	@PostConstruct
	public void init() {
		transaction = new Transactions();
	}

	public String addSaleRecipt(FullTimeEmployee employee) {
		
		transaction.setEmployee(employee);
		
		if (employee.getRole().equals("Monthly")) {
			transaction.setAmount((double)(saleAmount*employee.getCommissionRate()/100));
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

	public double getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(double saleAmount) {
		this.saleAmount = saleAmount;
	}

	public int getHoursAmount() {
		return hoursAmount;
	}

	public void setHoursAmount(int hoursAmount) {
		this.hoursAmount = hoursAmount;
	}
	
}
