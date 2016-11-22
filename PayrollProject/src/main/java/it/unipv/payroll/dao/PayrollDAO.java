package it.unipv.payroll.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unipv.payroll.model.Payroll;

@Stateless
public class PayrollDAO {

	@PersistenceContext
	EntityManager em;
	
	public List<Payroll> findAll() {
		
		// JPQL Query
		//perché la select ha un "p" eppure funziona? perché passo il PhoneBook.class? È per ottenere @table e l'entità Phonebook?
		List<Payroll> phoneBooks =
				em.createQuery("select p from Payroll p", Payroll.class)
				.getResultList();
		
		return phoneBooks;
	}

	public void add(Payroll payroll) {
		em.persist(payroll);
	}

	public void remove(Payroll pr) {
		em.remove(em.find(Payroll.class, pr.getId()));
	}
	
}
