package it.unipv.payroll.view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.SalesReceiptController;
import it.unipv.payroll.model.SalesReceipt;

@Named
@SessionScoped
public class SalesReceiptBean implements Serializable {
	@Inject
	SalesReceiptController srController;

	private SalesReceipt salesReceipt;

	public String postSalesReceipt() {

		String answer = srController.add(salesReceipt);
		return answer;
	}

	public SalesReceipt findSalesReceiptById(int id) {
		return srController.findSalesReceiptById(id);
	}

	public void setSalesReceipt(SalesReceipt salesReceipt) {
		this.salesReceipt = salesReceipt;
	}

	public SalesReceipt getSalesReceipt() {
		return salesReceipt;
	}
}
