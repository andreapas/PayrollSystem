package it.unipv.payroll.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unipv.payroll.model.Payroll;
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

	public void add(UserTransactions ut) {
		em.persist(ut);
	}

	public void update(UserTransactions aTransaction) {
		em.merge(aTransaction);		
	}

	public void remove(UserTransactions transaction) {
		em.remove(em.find(UserTransactions.class, transaction.getCode()));
		
	}

	/*public void remove(Payroll pr) {
		em.remove(em.find(Payroll.class, pr.getId()));
	}*/
	
}
