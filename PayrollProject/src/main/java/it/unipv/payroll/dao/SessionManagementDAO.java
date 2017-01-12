package it.unipv.payroll.dao;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Credentials;

@Stateless
public class SessionManagementDAO extends GenericDAO<Credentials>{

	public SessionManagementDAO() {
		super(Credentials.class);
	}
}
