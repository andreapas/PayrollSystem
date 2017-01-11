package it.unipv.payroll.dao;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Address;

@Stateless
public class AddressDAO extends GenericDAO<Address>{
	
	public AddressDAO() {
		super(Address.class);
	}

}
