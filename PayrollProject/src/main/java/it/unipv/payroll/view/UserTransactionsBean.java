package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.UserTransactionsController;
import it.unipv.payroll.dao.UserTransactionsDAO;
import it.unipv.payroll.model.UserTransactions;

@Named
@SessionScoped
public class UserTransactionsBean implements Serializable{

	@Inject UserTransactionsController utController;
	
	private UserTransactions aTransaction;
	
	private List<UserTransactions> allTransactions;

	public UserTransactions getTransaction() {
		return aTransaction;
	}

	public void setTransaction(UserTransactions aTransaction) {
		this.aTransaction = aTransaction;
	}

	public List<UserTransactions> getAllTransactions() {
		return allTransactions;
	}

	public void setAllTransactions(List<UserTransactions> allTransactions) {
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
