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
		logger.info("Payroll ready to receive new commands!");
	}

	public List<Payroll> addPayroll(Payroll payroll) throws Exception{
		
		if (payroll.getMessage().isEmpty()||payroll.getMessage().equals(null)) {
			throw new Exception("Message cannot be empty");
		}
		
		payrollDao.add(payroll);
		
		List<Payroll> payrollItems= payrollDao.findAll();
		
		return payrollItems;
	}

	public List<Payroll> remove(Payroll pr) {
		
		payrollDao.remove(pr.getId());
		List<Payroll> payrollItems= payrollDao.findAll();
		
		return payrollItems;
		
		
	}
}
