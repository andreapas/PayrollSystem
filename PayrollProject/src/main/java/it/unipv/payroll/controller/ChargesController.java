package it.unipv.payroll.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Charges;
import it.unipv.payroll.model.IEmployee;
import it.unipv.payroll.model.ITransaction;

@Stateless
public class ChargesController extends GenericController<Charges> {

	
	public HashMap<String, Float> chargeFee(List<String> codesToChargeTo) throws Exception{
		List<Charges> list=findAll();
		HashMap<String, Float> dues=new HashMap<String, Float>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Charges charges = (Charges) iterator.next();
			if (!charges.isExecuted()) {
				IEmployee em=charges.getEmployee();
				if (codesToChargeTo.contains(em.getCode())) {
					if (!dues.containsKey(em.getCode())) {
						dues.put(em.getCode(), charges.getAmount());
					}else{
						dues.put(em.getCode(), dues.get(em.getCode())+charges.getAmount());
					}
					charges.setExecuted(true);
					super.update(charges);
				}
			}
		}
		return dues;
	}
	
	
	public void addCharge(Charges element) throws Exception{
		element.setDate(new Date());
		super.add(element);
	}
	
	public List<Charges> findAll() {
		return dao.findAll();
	}

	
	@Override
	public boolean isAlreadyInDatabase(Charges element) {
		ITransaction tinfo = dao.find(element.getId());
		if (tinfo != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isElementOk(Charges element) {
		if (element.getEmployee() == null)
			return false;
		if (element.getDate() == null)
			return false;
		if (element.getInfo().isEmpty() || element.getInfo() == null)
			return false;
		return true;
	}

	@Override
	public Charges find(Object id) throws Exception {
		return dao.find(id);
	}

}
