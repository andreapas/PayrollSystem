package it.unipv.payroll.controller;

import java.util.List;

import javax.ejb.Stateless;

import it.unipv.payroll.model.TransactionsInfo;

@Stateless
public class TransactionsInfoController extends GenericController<TransactionsInfo> {
	
	//TODO: verify the usefulness of this method
	public List<TransactionsInfo> findAll() {
		return dao.findAll();
	}

}
