package it.unipv.payroll.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.ChargesController;
import it.unipv.payroll.model.Charges;
import it.unipv.payroll.model.IEmployee;

@Named
@RequestScoped
public class ChargesTransactionBean implements Serializable{

	@Inject
	ChargesController cController;
	
	private Charges charge;
	private IEmployee employee;

	
	@PostConstruct
	public void init(){
		charge=new Charges();
	}
	
	public Charges getCharge() {
		return charge;
	}
	
	public IEmployee getEmployee() {
		return employee;
	}
	public void setEmployee(IEmployee employee) {
		this.employee = employee;
		
	}
	public void addCharge() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			charge.setEmployee(employee);
			cController.addCharge(charge);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
					"The service charge has been successfully set"));

		} catch (Exception e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
							"Something has gone wrong while trying to add the service charge. The complete message is "
									+ e.getMessage()));
		}
	}
}
