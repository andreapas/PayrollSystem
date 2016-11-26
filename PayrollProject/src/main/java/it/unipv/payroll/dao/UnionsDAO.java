package it.unipv.payroll.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unipv.payroll.model.Payroll;
import it.unipv.payroll.model.Union;

@Stateless
public class UnionsDAO extends GenericDAO<Union> {

	public UnionsDAO() {
		super(Union.class);
	}
}
