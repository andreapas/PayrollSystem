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
		return allTransactions;
	}

	public void setAllTransactions(List<EmployeeTransactions> allTransactions) {
		this.allTransactions = allTransactions;
	}

	public void addTransaction() {
		try {
			allTransactions= utController.addTransaction(aTransaction);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addFee(int fee) {
		
		aTransaction.setFee(aTransaction.getFee()+fee);
		allTransactions= utController.updateTransaction(aTransaction);
		
	}

	public void addEarned(int earned) {
		aTransaction.setEarned(aTransaction.getEarned()+earned);
		allTransactions= utController.updateTransaction(aTransaction);
		
	}

	public HashMap<String, Integer> startPayday() {
		HashMap<String, Integer>employeesEarnings=utController.startPayday();
		
		return employeesEarnings;
	}
	
	
	

	
	
	
}
