package it.unipv.payroll.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unipv.payroll.model.EmployeeTransactions;

@Stateless
public class EmployeeTransactionsDAO {
	
	@PersistenceContext
	EntityManager em;

	
	//TODO:ADD GENERICS <T>
	
	
	public List<EmployeeTransactions> findAll() {
		
		List<EmployeeTransactions> userTransactions =
				em.createQuery("select et from EmployeeTransactions et", EmployeeTransactions.class)
				.getResultList();
		
		return userTransactions;
	}

	public void add(EmployeeTransactions aTransaction) {
		em.persist(aTransaction);
	}

	public void update(EmployeeTransactions aTransaction) {
		em.merge(aTransaction);		
	}

	public void remove(EmployeeTransactions aTransaction) {
		em.remove(em.find(EmployeeTransactions.class, aTransaction.getCode()));
		
	}
	
}
