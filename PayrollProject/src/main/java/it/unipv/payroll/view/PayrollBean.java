package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.PayrollController;
import it.unipv.payroll.model.Payroll;

@Named
@SessionScoped
public class PayrollBean implements Serializable {

	@Inject PayrollController payrollController;
	
	private Payroll payroll;
	
	private List<Payroll> payrollItems;
	
	@PostConstruct
	public void init() {
		payroll = new Payroll();
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

	public String addMessage() {
		String answer= payrollController.add(payroll);
		payrollItems=payrollController.findAll();
		return answer;
	}

	public String remove(Payroll pr) {
		String answer= payrollController.remove(pr.getId());
		payrollItems=payrollController.findAll();
		return answer;
	}
	
	public String testRedirect(){
		return "/test.xhtml?faces-redirect=true";
	}
	
	public String testGrowl(){
		return "Oh, sono un growl!";
	}
	
}
