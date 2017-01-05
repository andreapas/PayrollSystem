package it.unipv.payroll.controller;

import java.util.List;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.Payroll;

@Stateless
public class PayrollController extends GenericController<Payroll>{
	
	
	
	public List<Payroll> findAll() {
		return dao.findAll();
	}
	
	@Override
	public boolean isAlreadyInDatabase(Payroll element) {
		Payroll payroll= dao.find(element.getId());
		if(payroll!=null){
			return true;
		}
		return false;
	}
}
