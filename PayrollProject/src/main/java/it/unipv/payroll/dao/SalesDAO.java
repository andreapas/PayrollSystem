package it.unipv.payroll.dao;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Sales;

@Stateless
public class SalesDAO extends GenericDAO<Sales> {

	public SalesDAO() {
		super(Sales.class);
	}
	
	
}
