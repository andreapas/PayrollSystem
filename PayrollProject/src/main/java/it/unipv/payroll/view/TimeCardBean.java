package it.unipv.payroll.view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.TimeCardController;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.TimeCard;

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

	public String postTimeCard(Employee employee) {
		timeCard.setEmployee(employee);
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
