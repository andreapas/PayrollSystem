package it.unipv.payroll.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.plaf.synth.SynthSeparatorUI;

import it.unipv.payroll.controller.TransactionsController;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.Transactions;

@Named
@SessionScoped
public class TransactionsBean implements Serializable {

	@Inject TransactionsController tController;

	private Transactions transaction;
	private int id;

	@PostConstruct
	public void init() {
		transaction = new Transactions();
	}

	public String addTransaction(Employee employee){
		transaction.setEmployee(employee);
		System.out.println(employee.getRole());
		if (employee.getRole().equals("Monthly")) {
			transaction.setInfo("Sale ID="+id);
		}
		if (employee.getRole().equals("Weekly")) {
			transaction.setInfo("Extra hours");
		}
		else {
			System.out.println("Errrrore!");
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
}
