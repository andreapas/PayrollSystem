package it.unipv.payroll.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import it.unipv.payroll.dao.PayrollDAO;
import it.unipv.payroll.model.Payroll;

@Stateless
public class PayrollController {

	
	@Inject	PayrollDAO payrollDao;
	Logger logger = Logger.getLogger(PayrollController.class);
	
	@PostConstruct
	public void init() {
		logger.info("PhoneBook Controller is up!");
	}
	
	
	public List<Payroll> refreshPage(){
		payrollDao.findAll();
		List<Payroll> items= payrollDao.findAll();
		return items;
	}
}
