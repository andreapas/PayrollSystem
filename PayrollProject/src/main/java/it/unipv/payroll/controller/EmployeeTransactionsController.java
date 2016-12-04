package it.unipv.payroll.controller;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;

import it.unipv.payroll.model.EmployeeTransactions;

@Stateless
public class EmployeeTransactionsController extends GenericController<EmployeeTransactions> {

	private static String SUCCESS="Success";
	private static String ERROR="Id not found";
	
	public HashMap<String, Integer> startPayday() {
		HashMap<String, Integer> employeesEarnings= new HashMap<String, Integer>();
		List<EmployeeTransactions> allTransactions= dao.findAll();
		for (EmployeeTransactions ut : allTransactions) {
			employeesEarnings.put(ut.getCode(), ut.getEarned()-ut.getFee());
			ut.setEarned(0);
			ut.setFee(0);
			dao.update(ut);
		}
		return employeesEarnings;
	}
	
	
	public String addFee(int fee, String id){
		EmployeeTransactions em;
		em=dao.find(id);
		if(em!=null){
			em.setFee(em.getFee()+fee);
			dao.update(em);
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	public String addEarned(int earned, String id){
		EmployeeTransactions em;
		em=dao.find(id);
		if(em!=null){
			em.setEarned(em.getEarned()+earned);
			dao.update(em);
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	public List<EmployeeTransactions> findAll(){
		return dao.findAll();
	}

	

}
