package it.unipv.payroll.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.controller.SalesController;
import it.unipv.payroll.model.IEmployee;
import it.unipv.payroll.model.Sales;

@Named
@ViewScoped
@Stateful
public class SalesTransactionBean implements Serializable {

	@Inject
	SalesController sController;
	@Inject
	EmployeeController emController;
	private IEmployee loggedUser;
	private Sales sale;

	@PostConstruct
	public void init() {
		sale = new Sales();
		try {
			loggedUser = emController.find(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Sales getSale() {
		return sale;
	}

	public void addSaleRecipt() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			sale.setEmployee(loggedUser);
			sController.addSale(sale);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
					"The sale receipt have been correctly added"));
		} catch (Exception e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
							"Something has gone wrong while trying to add sale receipt. The complete message is "
									+ e.getMessage()));
		}

	}
}
