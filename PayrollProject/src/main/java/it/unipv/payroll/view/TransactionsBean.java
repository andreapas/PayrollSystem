package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.controller.TransactionsController;
import it.unipv.payroll.model.Transactions;

@Named
@SessionScoped
public class TransactionsBean implements Serializable {

	@Inject TransactionsController tController;
	@Inject EmployeeController eController;

	private Transactions transaction;
	
	@PostConstruct
	public void init() {
		transaction = new Transactions();
	}

	public String addTransaction(){
		transaction.setEmployee(eController.find(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()));
		String answer = tController.add(transaction);
		return answer;
	}

	public Transactions getTransaction() {
		return transaction;
	}
	public void setTransaction(Transactions transaction) {
		this.transaction = transaction;
	}
}
