package it.unipv.payroll.dao;

import javax.ejb.Stateless;

import it.unipv.payroll.model.EmployeeTransactions;

@Stateless
public class EmployeeTransactionsDAO extends GenericDAO<EmployeeTransactions>{

	public EmployeeTransactionsDAO() {
		super(EmployeeTransactions.class);
	}
	
}
