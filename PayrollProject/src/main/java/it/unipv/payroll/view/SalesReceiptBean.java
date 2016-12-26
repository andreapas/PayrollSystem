package it.unipv.payroll.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.controller.SalesReceiptController;
import it.unipv.payroll.model.SalesReceipt;
import it.unipv.payroll.model.TransactionsInfo;

@Named
@SessionScoped
public class SalesReceiptBean implements Serializable {
	@Inject
	SalesReceiptController srController;
	@Inject
	EmployeeController employeeController;

	private SalesReceipt salesReceipt;

	@PostConstruct
	public void init() {
		salesReceipt = new SalesReceipt();
	}
	
	public String postSalesReceipt() {
		salesReceipt.setEmployee(employeeController.find(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()));
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
