package it.unipv.payroll.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.TimeCardController;
import it.unipv.payroll.controller.TransactionsInfoController;
import it.unipv.payroll.model.TimeCard;
import it.unipv.payroll.model.TransactionsInfo;

@Named
@SessionScoped
public class TimeCardBean implements Serializable {

	@Inject
	TimeCardController tcController;

	private TimeCard timeCard;
//	@PostConstruct
//	public void init() {
//		timeCard= new TimeCard();
//	}

	public String postTimeCard() {
		//timeCard.setEmployeeCode(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
		String answer = tcController.add(timeCard);
		return answer;
	}
	
	public TimeCard findCardById(int id){
		return tcController.findCardById(id);
	}

	public void setTimeCard(TimeCard timeCard) {
		this.timeCard=timeCard;
	}
	public TimeCard getTimeCard() {
		return timeCard;
	}

}
