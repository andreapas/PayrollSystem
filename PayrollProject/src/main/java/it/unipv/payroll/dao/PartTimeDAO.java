package it.unipv.payroll.dao;

import javax.ejb.Stateless;

import it.unipv.payroll.model.PartTimeEmployee;

@Stateless
public class PartTimeDAO extends GenericDAO<PartTimeEmployee>{

	public PartTimeDAO() {
		super(PartTimeEmployee.class);
	}
	
}
