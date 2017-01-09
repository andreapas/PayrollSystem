package it.unipv.payroll.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import it.unipv.payroll.model.Transactions;

@Stateless
public class TransactionsDAO extends GenericDAO<Transactions>{

	public TransactionsDAO() {
		super(Transactions.class);
	}
	
	//ONE TO MANY MANY TO ONE
	public List<Transactions> findAllTransactionFromEmployeeCode(String id){
//		criteriaQuery.select(criteriaQuery.from(type));
//		return em.createQuery(criteriaQuery).getResultList();
		CriteriaQuery<Transactions> q = em.getCriteriaBuilder().createQuery(Transactions.class);
		  Root<Transactions> c = q.from(Transactions.class);
		  q.select(c);
		  ParameterExpression<String> p = em.getCriteriaBuilder().parameter(String.class);
		  q.where(em.getCriteriaBuilder().equal(c.get(id), p));
		return em.createQuery(q).getResultList();
	};

}
