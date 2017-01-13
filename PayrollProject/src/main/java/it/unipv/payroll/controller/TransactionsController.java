package it.unipv.payroll.controller;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Transactions;

@Stateless
public class TransactionsController extends GenericController<Transactions> {
	
	//TODO: verify the usefulness of this method
	public List<Transactions> findAll() {
		return dao.findAll();
	}
	
	public HashMap<String, Double> startPayday() {
		HashMap<String, Double> employeesEarnings= new HashMap<String, Double>();
		List<Transactions> allTransactions= dao.findAll();
		for (Transactions ut : allTransactions) {
			
			if(employeesEarnings.containsKey(ut.getEmployee().getCode())){
				employeesEarnings.put(ut.getEmployee().getCode(),employeesEarnings.get(ut.getEmployee().getCode())+ut.getAmount());
			}else{
				employeesEarnings.put(ut.getEmployee().getCode(),ut.getAmount());
			}
			ut.setExecuted(true);
			dao.update(ut);
		}
		return employeesEarnings;
	}
	
	@Override
	public boolean isAlreadyInDatabase(Transactions element) {
		Transactions tinfo= dao.find(element.getId());
		if(tinfo!=null){
			return true;
		}
		return false;
	}

}
