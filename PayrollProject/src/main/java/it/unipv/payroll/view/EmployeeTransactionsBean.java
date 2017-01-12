package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import it.unipv.payroll.controller.EmployeeTransactionsController;
import it.unipv.payroll.model.EmployeeTransactions;

@Named
@SessionScoped
public class EmployeeTransactionsBean implements Serializable {

	@Inject
	EmployeeTransactionsController utController;

	private EmployeeTransactions aTransaction;

	private List<EmployeeTransactions> allTransactions;
	private List<String> transList;
	private HashMap<String, Integer> transMap;

	@PostConstruct
	public void init(){
		transList=new ArrayList<String>();
	}
	
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

		String answer = utController.add(aTransaction);
		return answer;
	}

	public String addFee(int fee, String id) {

		String answer = utController.addFee(fee, id);
		return answer;

	}

	public String addEarned(int earned, String id) {
		String answer = utController.addEarned(earned, id);
		return answer;
	}

	public HashMap<String, Integer> startPayday() {
		HashMap<String, Integer> employeesEarnings = utController.startPayday();
		List<String> tmpList=new ArrayList<String>();
		tmpList.addAll(employeesEarnings.keySet());
		transList=tmpList;
		transMap=employeesEarnings;
		return employeesEarnings;
	}
	public List<String> getTransList() {
		return transList;
	}
	public HashMap<String, Integer> getTransMap() {
		return transMap;
	}
	public boolean isPayday() {
		// some logic
		return true;
	}

}
