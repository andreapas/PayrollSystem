package it.unipv.payroll.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;

import it.unipv.payroll.model.FullTimeEmployee;
import it.unipv.payroll.model.IEmployee;
import it.unipv.payroll.model.ITransaction;
import it.unipv.payroll.model.Salary;
import it.unipv.payroll.model.Sales;

@Stateless
public class SalesController extends GenericController<Sales> {

	public List<Sales> findAll() {
		List<Sales> salesList=dao.findAll();
		return salesList;
	}
	
	public void addSale(Sales element) throws Exception{
		float sold=element.getAmount();
		float rate=((FullTimeEmployee)element.getEmployee()).getCommissionRate()/((float)100);
		
		element.setAmount(sold*rate);
		super.add(element);
	}
	public HashMap<String, Float> paySales(List<String> codesToPayTo) throws Exception{
		List<Sales> list=findAll();
		HashMap<String, Float> earnings=new HashMap<String, Float>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Sales salary = (Sales) iterator.next();
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
	public boolean isAlreadyInDatabase(Sales element) {
		ITransaction tinfo = dao.find(element.getId());
		if (tinfo != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isElementOk(Sales element) {
		if (element.getEmployee() == null)
			return false;
		if (element.getDate() == null)
			return false;
		if (element.getInfo().isEmpty() || element.getInfo() == null)
			return false;
		return true;
	}

	@Override
	public Sales find(Object id) throws Exception {
		return dao.find(id);
	}

}
