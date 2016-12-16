package it.unipv.payroll.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import it.unipv.payroll.model.TransactionsInfo;

@Stateless
public class TransactionsInfoDAO extends GenericDAO<TransactionsInfo>{

	public TransactionsInfoDAO() {
		super(TransactionsInfo.class);
	}
	
	//ONE TO MANY MANY TO ONE
	public List<TransactionsInfo> findAllTransactionFromEmployeeCode(String id){
//		criteriaQuery.select(criteriaQuery.from(type));
//		return em.createQuery(criteriaQuery).getResultList();
		CriteriaQuery<TransactionsInfo> q = em.getCriteriaBuilder().createQuery(TransactionsInfo.class);
		  Root<TransactionsInfo> c = q.from(TransactionsInfo.class);
		  q.select(c);
		  ParameterExpression<String> p = em.getCriteriaBuilder().parameter(String.class);
		  q.where(em.getCriteriaBuilder().equal(c.get(id), p));
		return em.createQuery(q).getResultList();
	};

}
