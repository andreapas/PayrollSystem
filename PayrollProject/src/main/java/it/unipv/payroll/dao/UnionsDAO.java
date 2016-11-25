package it.unipv.payroll.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unipv.payroll.model.Union;

@Stateless
public class UnionsDAO {

	@PersistenceContext
	EntityManager em;
	
	public List<Union> getUnions() {
		
		List<Union> unionsList = em.createQuery("select p from Union p", Union.class).getResultList();
		
		return unionsList;
	}

	public void add(Union union) {
		em.persist(union);
	}

	public void remove(Union union) {
		em.remove(em.find(Union.class, union.getUnionName()));
	}
}
