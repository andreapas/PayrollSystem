package it.unipv.payroll.controller;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Payroll;
import it.unipv.payroll.model.SalesReceipt;


@Stateless
public class SalesReceiptController extends GenericController<SalesReceipt>{

	public SalesReceipt findSalesReceiptById(int salesReceiptId){
		return dao.find(salesReceiptId);
	}
	@Override
	public boolean isAlreadyInDatabase(SalesReceipt element) {
		SalesReceipt sreceipt= dao.find(element.getPostId());
		if(sreceipt!=null){
			return true;
		}
		return false;
	}
}
