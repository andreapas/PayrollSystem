package it.unipv.payroll.dao;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Charges;

@Stateless
public class ChargesDAO extends GenericDAO<Charges> {

	
	public ChargesDAO() {
		super(Charges.class);
	}
}
