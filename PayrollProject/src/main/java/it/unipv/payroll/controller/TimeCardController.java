package it.unipv.payroll.controller;

import javax.ejb.Stateless;

import it.unipv.payroll.model.TimeCard;

@Stateless
public class TimeCardController extends GenericController<TimeCard>{

	public TimeCard findCardById(int cardId){
		return dao.find(cardId);
	}
}
