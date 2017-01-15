package it.unipv.payroll.dao;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Transactions;

@Stateless
public class TransactionsDAO extends GenericDAO<Transactions>{

	public TransactionsDAO() {
		super(Transactions.class);
	}

}
