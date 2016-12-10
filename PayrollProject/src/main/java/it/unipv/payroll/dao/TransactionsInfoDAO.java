package it.unipv.payroll.dao;

import javax.ejb.Stateless;

import it.unipv.payroll.model.TransactionsInfo;

@Stateless
public class TransactionsInfoDAO extends GenericDAO<TransactionsInfo>{

	public TransactionsInfoDAO() {
		super(TransactionsInfo.class);
	}

}
