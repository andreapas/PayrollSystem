package it.unipv.payroll.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import it.unipv.payroll.dao.PayrollDAO;
import it.unipv.payroll.dao.UserTransactionsDAO;
import it.unipv.payroll.model.Payroll;
import it.unipv.payroll.model.UserTransactions;

@Stateless
public class UserTransactionsController {

	@Inject	UserTransactionsDAO utDao;
	Logger logger = Logger.getLogger(PayrollController.class);
	
	@PostConstruct
	public void init() {
		logger.info("UserTransactionController ready to receive new commands!");
	}
	
	/*public List<Payroll> remove(Payroll pr) {
		
		payrollDao.remove(pr);
		List<Payroll> payrollItems= payrollDao.findAll();
		
		return payrollItems;
		
		
	}*/
	
	public List<UserTransactions> addTransaction(UserTransactions aTransaction) throws Exception{
		
		if (aTransaction.getCode().isEmpty()||aTransaction.getCode().equals(null)) {
			throw new Exception("Code cannot be null");
		}
		
		utDao.add(aTransaction);
		
		List<UserTransactions> allTransactions= utDao.findAll();
		
		return allTransactions;
	}
	
	public List<UserTransactions> updateTransaction(UserTransactions aTransaction){
		utDao.update(aTransaction);
		
		List<UserTransactions> allTransactions= utDao.findAll();
		
		return allTransactions;

	}

	public HashMap<String, Integer> startPayday() {
		HashMap<String, Integer> employeesEarnings= new HashMap<String, Integer>();
		List<UserTransactions> allTransactions= utDao.findAll();
		for (UserTransactions ut : allTransactions) {
			employeesEarnings.put(ut.getCode(), ut.getEarned()-ut.getFee());
			ut.setEarned(0);
			ut.setFee(0);
			utDao.update(ut);
		}
		return employeesEarnings;
	}

}
