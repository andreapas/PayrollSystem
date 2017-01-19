package it.unipv.payroll.dao;

import it.unipv.payroll.model.Transaction;

public class TransactionDAO extends GenericDAO<Transaction> {

	
	public TransactionDAO() {
		super(Transaction.class);
	}
}
