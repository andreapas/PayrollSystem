package it.unipv.payroll.dao;

import javax.ejb.Stateless;

import it.unipv.payroll.model.FullTimeEmployee;

@Stateless
public class FullTimeDAO  extends GenericDAO<FullTimeEmployee>{

	
	public FullTimeDAO() {
		super(FullTimeEmployee.class);
	}
}
