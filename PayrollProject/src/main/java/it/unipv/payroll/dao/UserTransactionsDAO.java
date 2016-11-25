package it.unipv.payroll.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unipv.payroll.model.UserTransactions;

@Stateless
public class UserTransactionsDAO {
	
	@PersistenceContext
	EntityManager em;

	
	//TODO:ADD GENERICS <T>
	
	
	public List<UserTransactions> findAll() {
		
		List<UserTransactions> userTransactions =
				em.createQuery("select ut from UserTransactions ut", UserTransactions.class)
				.getResultList();
		
		return userTransactions;
	}

	public void add(UserTransactions aTransaction) {
		em.persist(aTransaction);
	}

	public void update(UserTransactions aTransaction) {
		em.merge(aTransaction);		
	}

	public void remove(UserTransactions aTransaction) {
		em.remove(em.find(UserTransactions.class, aTransaction.getCode()));
		
	}
	
}
