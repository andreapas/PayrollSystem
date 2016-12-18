package it.unipv.payroll.controller;

import javax.ejb.Stateless;

import it.unipv.payroll.model.SalesReceipt;


@Stateless
public class SalesReceiptController extends GenericController<SalesReceipt>{

	public SalesReceipt findSalesReceiptById(int salesReceiptId){
		return dao.find(salesReceiptId);
	}
}
