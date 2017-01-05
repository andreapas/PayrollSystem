package it.unipv.payroll.controller;

import javax.ejb.Stateless;

import it.unipv.payroll.model.SalesReceipt;
import it.unipv.payroll.model.TimeCard;

@Stateless
public class TimeCardController extends GenericController<TimeCard>{

	public TimeCard findCardById(int cardId){
		return dao.find(cardId);
	}
	
	@Override
	public boolean isAlreadyInDatabase(TimeCard element) {
		TimeCard tcard= dao.find(element.getPostId());
		if(tcard!=null){
			return true;
		}
		return false;
	}
}
