package it.unipv.payroll.controller;

import java.util.List;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Payroll;

@Stateless
public class PayrollController extends GenericController<Payroll>{
	
	
	public PayrollController() {
		super(Payroll.class);
	}
	
	public List<Payroll> findAll() {
		return dao.findAll();
	}
}
