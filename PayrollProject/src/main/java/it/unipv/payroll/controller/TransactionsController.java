package it.unipv.payroll.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;

import it.unipv.payroll.model.FullTimeEmployee;
import it.unipv.payroll.model.PartTimeEmployee;
import it.unipv.payroll.model.Transactions;

@Stateless
public class TransactionsController extends GenericController<Transactions> {

	public void addSale(Transactions element) throws Exception{
		float sold=element.getAmount();
		float rate=((FullTimeEmployee)element.getEmployee()).getCommissionRate()/((float)100);
		
		element.setAmount(sold*rate);
		super.add(element);
	}

	public void addHours(Transactions element, int numberOfHours) throws Exception {
		element.setDate(new Date());
		float rate=((PartTimeEmployee)element.getEmployee()).getHourlyRate();
		if (numberOfHours > 8) {
			element.setAmount((float) (8 * rate + 1.5 * (numberOfHours - 8) * rate));
		} else {
			element.setAmount((float) (numberOfHours * rate));
		}
		element.setInfo("Worked hours");
		super.add(element);
		
	}

	public void addCharge(Transactions element) throws Exception{
		element.setDate(new Date());
		element.setAmount(-element.getAmount());
		super.add(element);
	}
	
	public List<Transactions> findAll() {
		return dao.findAll();
	}

	@Override
	public boolean isAlreadyInDatabase(Transactions element) {
		Transactions tinfo = dao.find(element.getId());
		if (tinfo != null) {
			return true;
		}
		return false;
	}

	@Override
	public Transactions find(Object id) throws Exception {
		return dao.find(id);
	}

	public HashMap<String, Float> pay(String role) throws Exception {
		HashMap<String, Float> earnings = new HashMap<String, Float>();
		for (Transactions ut : findAll()) {
			if (!ut.isExecuted()) {

				if (ut.getEmployee().getRole().equals(role)) {
					if (earnings.containsKey(ut.getEmployee().getCode())) {
						float tot=earnings.get(ut.getEmployee().getCode())+ut.getAmount();
						earnings.put(ut.getEmployee().getCode(), tot);
					} else {
						earnings.put(ut.getEmployee().getCode(), ut.getAmount());
					}
					ut.setExecuted(true);
					update(ut);
				}
			}
		}
		return earnings;
	}

	@Override
	public boolean isElementOk(Transactions element) {
		if (element.getEmployee() == null)
			return false;
		if (element.getDate() == null)
			return false;
		if (element.getInfo().isEmpty() || element.getInfo() == null)
			return false;
		return true;

	}
}
