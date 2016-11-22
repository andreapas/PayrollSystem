package it.unipv.payroll.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.PayrollController;
import it.unipv.payroll.dao.PayrollDAO;
import it.unipv.payroll.model.Payroll;

@Named
@SessionScoped
public class PayrollBean {

	@Inject PayrollController payrollController;
	@Inject PayrollDAO payrollDAO;
	
	private Payroll payroll;
	
	private List<Payroll> payrollItems;
	
	@PostConstruct
	public void init() {
		payroll = new Payroll();
		payrollItems = payrollDAO.findAll();
	}

	public Payroll getPayroll() {
		return payroll;
	}

	public void setPayroll(Payroll payroll) {
		this.payroll = payroll;
	}

	public List<Payroll> getPayrollItems() {
		return payrollItems;
	}

	public void setPayrollItems(List<Payroll> payrollItems) {
		this.payrollItems = payrollItems;
	}
	
	
}
