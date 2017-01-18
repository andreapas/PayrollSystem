package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.controller.TransactionsController;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.Transactions;

@Named
@RequestScoped
public class TransactionsBean implements Serializable {

	@Inject
	TransactionsController tController;
	@Inject
	EmployeeController emController;

	private Transactions transaction;
	// private double saleAmount;
	private List<Transactions> transactionList;
	private int hoursAmount;
	private Employee employee;
	// private Employee loggedEmployee;

	@PostConstruct
	public void init() {
		transaction = new Transactions();
		transactionList = new ArrayList<Transactions>();
		// try {
		// loggedEmployee=emController.find(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public void addSaleRecipt() {
		// transaction.setEmployee(loggedEmployee);
		// transaction.setInfo("Sale ID="+id);
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			tController.addSale(transaction);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
					"The sale receipt have been correctly added"));
			transactionList = tController.findAll();
		} catch (Exception e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
							"Something has gone wrong while trying to add sale receipt. The complete message is "
									+ e.getMessage()));
		}

	}

	public void addHours() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			tController.addHours(transaction, hoursAmount);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
					"The worked hours have been correctly added"));
			transactionList = tController.findAll();
		} catch (Exception e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
							"Something has gone wrong while trying to add the hours. The complete message is "
									+ e.getMessage()));

		}
	}

	public Transactions getTransaction() {
		return transaction;
	}

	public void setTransaction(Transactions transaction) {
		this.transaction = transaction;
	}

	// public double getSaleAmount() {
	// return saleAmount;
	// }
	//
	// public void setSaleAmount(double saleAmount) {
	// this.saleAmount = saleAmount;
	// }

	public int getHoursAmount() {
		return hoursAmount;
	}

	public void setHoursAmount(int hoursAmount) {
		this.hoursAmount = hoursAmount;
	}

	public List<Transactions> getAllTransactions() {
		return transactionList;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void addServiceCharge() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			transaction.setEmployee(employee);
			tController.addCharge(transaction);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
					"The service charge has been successfully set"));

			transactionList = tController.findAll();
		} catch (Exception e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
							"Something has gone wrong while trying to add the service charge. The complete message is "
									+ e.getMessage()));
		}
	}
	
}
