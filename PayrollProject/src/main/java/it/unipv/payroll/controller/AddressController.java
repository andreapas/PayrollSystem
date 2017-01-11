package it.unipv.payroll.controller;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Address;

@Stateless
public class AddressController extends GenericController<Address>{

	
	@Override
	public boolean isAlreadyInDatabase(Address element) {
		Address address= dao.find(element.getCode());
		if(address!=null){
			return true;
		}

		return false;
	}
}
