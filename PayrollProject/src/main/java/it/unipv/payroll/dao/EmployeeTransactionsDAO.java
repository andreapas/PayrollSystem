package it.unipv.payroll.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unipv.payroll.model.EmployeeTransactions;
import it.unipv.payroll.model.Payroll;

@Stateless
public class EmployeeTransactionsDAO extends GenericDAO<EmployeeTransactions>{

	public EmployeeTransactionsDAO() {
		super(EmployeeTransactions.class);
	}
	
}
