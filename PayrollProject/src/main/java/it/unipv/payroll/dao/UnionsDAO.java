package it.unipv.payroll.dao;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Union;

@Stateless
public class UnionsDAO extends GenericDAO<Union> {

	public UnionsDAO() {
		super(Union.class);
	}
}
