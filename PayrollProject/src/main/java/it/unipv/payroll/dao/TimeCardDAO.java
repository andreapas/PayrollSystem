package it.unipv.payroll.dao;

import javax.ejb.Stateless;

import it.unipv.payroll.model.TimeCard;

@Stateless
public class TimeCardDAO extends GenericDAO<TimeCard> {

	
	public TimeCardDAO() {
		super(TimeCard.class);
	}
}
