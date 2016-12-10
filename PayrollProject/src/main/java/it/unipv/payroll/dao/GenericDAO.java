package it.unipv.payroll.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

@TransactionAttribute(TransactionAttributeType.REQUIRED)
public abstract class GenericDAO<T extends Serializable> {

	private final Class<T> type;

	@PersistenceContext
	EntityManager em;


	public GenericDAO(Class<T> type) {
		this.type = type;
	}

	public void add(final T t) {
		em.persist(t);
	}

	public void update(T t) {
		em.merge(t);
	}

	public void remove(Object id) {
		em.remove(this.em.getReference(type, id));

	}

	public T find(Object id) {
		return em.find(type, id);
	}

	public List<T> findAll() {
		final CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(type);
		criteriaQuery.select(criteriaQuery.from(type));
		return em.createQuery(criteriaQuery).getResultList();
	}

}