package it.unipv.payroll.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;

import it.unipv.payroll.model.PartTimeEmployee;
import it.unipv.payroll.model.Salary;
import it.unipv.payroll.model.TimeCard;
import it.unipv.payroll.model.IEmployee;
import it.unipv.payroll.model.ITransaction;

@Stateless
public class TimeCardController extends GenericController<TimeCard> {

	
	public HashMap<String, Float> payTimeCards(List<String> codesToPayTo) throws Exception{
		List<TimeCard> list=findAll();
		HashMap<String, Float> earnings=new HashMap<String, Float>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TimeCard salary = (TimeCard) iterator.next();
			if (!salary.isExecuted()) {
				IEmployee em=salary.getEmployee();
				if (codesToPayTo.contains(em.getCode())) {
					if (!earnings.containsKey(em.getCode())) {
						earnings.put(em.getCode(), salary.getAmount());
					}else{
						earnings.put(em.getCode(), earnings.get(em.getCode())+salary.getAmount());
					}
					salary.setExecuted(true);
					super.update(salary);
				}
			}
		}
		return earnings;
	}
	
	public void addHours(TimeCard element) throws Exception {
		element.setDate(new Date());
		float rate=((PartTimeEmployee)element.getEmployee()).getHourlyRate();
		if (element.getHoursWorked()> 8) {
			element.setAmount((float) (8 * rate + 1.5 * (element.getHoursWorked()- 8) * rate));
		} else {
			element.setAmount((float) (element.getHoursWorked() * rate));
		}
		element.setInfo("Worked hours");
		super.add(element);
		
	}
	
	public List<TimeCard> findAll() {
		return dao.findAll();
	}
	@Override
	public boolean isAlreadyInDatabase(TimeCard element) {
		ITransaction tinfo = dao.find(element.getId());
		if (tinfo != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isElementOk(TimeCard element) {
		if (element.getEmployee() == null)
			return false;
		if (element.getDate() == null)
			return false;
		if (element.getInfo().isEmpty() || element.getInfo() == null)
			return false;
		return true;
	}

	@Override
	public TimeCard find(Object id) throws Exception {
		return dao.find(id);
	}

}
