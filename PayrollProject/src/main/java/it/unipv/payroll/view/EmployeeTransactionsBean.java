package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.EmployeeTransactionsController;
import it.unipv.payroll.dao.EmployeeTransactionsDAO;
import it.unipv.payroll.model.EmployeeTransactions;

@Named
@SessionScoped
public class EmployeeTransactionsBean implements Serializable{

	@Inject EmployeeTransactionsController utController;
	
	private EmployeeTransactions aTransaction;
	
	private List<EmployeeTransactions> allTransactions;

	public EmployeeTransactions getTransaction() {
		return aTransaction;
	}

	public void setTransaction(EmployeeTransactions aTransaction) {
		this.aTransaction = aTransaction;
	}

	public List<EmployeeTransactions> getAllTransactions() {
		allTransactions.addAll(utController.findAll());
		return allTransactions;
	}
	
	public String addTransaction() {
		
		String answer=utController.add(aTransaction);
		return answer;
	}

	public String addFee(int fee, String id) {
		
		String answer=utController.addFee(fee, id);
		return answer;
		
	}

	public String addEarned(int earned, String id) {
		String answer= utController.addEarned(earned,id);
		return answer;
	}

	public HashMap<String, Integer> startPayday() {
		HashMap<String, Integer>employeesEarnings=utController.startPayday();
		
		return employeesEarnings;
	}
	
	
	

	
	
	
}
