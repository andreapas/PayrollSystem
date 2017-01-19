package it.unipv.payroll.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Charges;
import it.unipv.payroll.model.FullTimeEmployee;
import it.unipv.payroll.model.IEmployee;
import it.unipv.payroll.model.ITransaction;
import it.unipv.payroll.model.Salary;

@Stateless
public class SalaryController extends GenericController<Salary> {
	
	public List<Salary> findAll() {
		return dao.findAll();
	}
	
	public HashMap<String, Float> paySalary(List<String> codesToPayTo) throws Exception{
		List<Salary> list=findAll();
		HashMap<String, Float> earnings=new HashMap<String, Float>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Salary salary = (Salary) iterator.next();
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
	@Override
	public boolean isAlreadyInDatabase(Salary element) {
		ITransaction tinfo = dao.find(element.getId());
		if (tinfo != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isElementOk(Salary element) {
		if (element.getEmployee() == null)
			return false;
		if (element.getDate() == null)
			return false;
		if (element.getInfo().isEmpty() || element.getInfo() == null)
			return false;
		return true;
	}

	@Override
	public Salary find(Object id) throws Exception {
		return dao.find(id);
	}

}
