package it.unipv.payroll.controller;

import java.util.HashMap;
import java.util.List;

import it.unipv.payroll.model.IEmployee;
import it.unipv.payroll.model.ITransaction;
import it.unipv.payroll.model.Transaction;

public class TransactionController extends GenericController<Transaction> {
	
	public List<Transaction> getTransactionList(){
		return dao.findAll();
	}
	
	
	@Override
	public boolean isAlreadyInDatabase(Transaction element) {
		ITransaction tinfo = dao.find(element.getId());
		if (tinfo != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isElementOk(Transaction element) {
		return false;
	}

	@Override
	public Transaction find(Object id) throws Exception {
		return dao.find(id);
	}

}
