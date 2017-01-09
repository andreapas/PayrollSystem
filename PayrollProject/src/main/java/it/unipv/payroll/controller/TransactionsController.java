package it.unipv.payroll.controller;

import java.util.List;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Transactions;

@Stateless
public class TransactionsController extends GenericController<Transactions> {
	
	//TODO: verify the usefulness of this method
	public List<Transactions> findAll() {
		return dao.findAll();
	}
	
	@Override
	public boolean isAlreadyInDatabase(Transactions element) {
		Transactions tinfo= dao.find(element.getId());
		if(tinfo!=null){
			return true;
		}
		return false;
	}

}
