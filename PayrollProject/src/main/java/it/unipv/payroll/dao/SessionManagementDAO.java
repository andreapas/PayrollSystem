package it.unipv.payroll.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unipv.payroll.model.Credentials;
import it.unipv.payroll.model.Payroll;

@Stateless
public class SessionManagementDAO extends GenericDAO<Credentials>{

	public SessionManagementDAO() {
		super(Credentials.class);
	}
}
