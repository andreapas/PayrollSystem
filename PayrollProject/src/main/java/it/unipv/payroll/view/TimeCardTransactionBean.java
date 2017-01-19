package it.unipv.payroll.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.controller.TimeCardController;
import it.unipv.payroll.mediator.ControllerMediator;
import it.unipv.payroll.model.IEmployee;
import it.unipv.payroll.model.TimeCard;

@Named
@ViewScoped
@Stateful
public class TimeCardTransactionBean implements Serializable {

//	@Inject
//	TimeCardController tcController;
//	@Inject
//	EmployeeController emController;
	private IEmployee loggedUser;
	private TimeCard timeCard;
	
	
	@PostConstruct
	public void init(){
		timeCard=new TimeCard();
		try {
			loggedUser = ControllerMediator.getMed().getEmController().find(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public TimeCard getTimeCard() {
		return timeCard;
	}
	public void addHours() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			timeCard.setEmployee(loggedUser);
			ControllerMediator.getMed().getTcController().addHours(timeCard);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
					"The worked hours have been correctly added"));
		} catch (Exception e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
							"Something has gone wrong while trying to add the hours. The complete message is "
									+ e.getMessage()));

		}
	}
}
