package it.unipv.payroll.view;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.ChargesController;
import it.unipv.payroll.mediator.ControllerMediator;
import it.unipv.payroll.model.Charges;
import it.unipv.payroll.model.IEmployee;

@Named
@ViewScoped
@Stateful
public class ChargesTransactionBean {

//	@Inject
//	ChargesController cController;
	
	private Charges charge;
	private IEmployee employee;

	
	@PostConstruct
	public void init(){
		charge=new Charges();
	}
	
	public Charges getCharge() {
		return charge;
	}
	
	public void setEmployee(IEmployee employee) {
		this.employee = employee;
	}
	public void addCharge() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			charge.setEmployee(employee);
			ControllerMediator.getMed().getcController().addCharge(charge);
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
