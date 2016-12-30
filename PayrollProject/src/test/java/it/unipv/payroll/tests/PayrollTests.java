package it.unipv.payroll.tests;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unipv.payroll.dao.PayrollDAO;
import it.unipv.payroll.model.Payroll;
import it.unipv.payroll.model.PayrollUsername;
import it.unipv.payroll.view.PayrollBean;

@RunWith(Arquillian.class)
public class PayrollTests extends ArquillianTest{

	@Inject PayrollBean payrollBean;
	@Inject PayrollDAO payrollDAO;
	
	
	
	@Test
	public void test() {
		// Set an element in the bean
		Payroll aPayroll= new Payroll();
		PayrollUsername aPayrollUsername = new PayrollUsername();
		aPayroll.setMessage("Hi, I'm a test message. Nice to meet you!");
		List<Payroll> payrolls=payrollDAO.findAll();
		aPayrollUsername.setUsername("Davide");
		aPayrollUsername.setPayrolls(payrolls);
		aPayrollUsername.setCity("Pavia");
		aPayroll.setUser_name(aPayrollUsername);
		payrollBean.setPayroll(aPayroll);
		
		payrollBean.addMessage();
		
		payrolls=payrollDAO.findAll();
		boolean found=false;
		for (Payroll pr : payrolls) {
			if ("Hi, I'm a test message. Nice to meet you!".equals(pr.getMessage())) {
				found=true;
				break;
			}
		}
		Assert.assertTrue("Polite message found!", found);
				
	}
	@Test
	public void testGrowlAndCatchClause(){
		Assert.assertEquals("probably you're testing...",payrollBean.testGrowl());
	}
	@Test
	public void testRemove(){
		List<Payroll> payrolls=payrollDAO.findAll();
		boolean found=false;
		for (Payroll pr : payrolls) {
			if ("Hi, I'm a test message. Nice to meet you!".equals(pr.getMessage())) {
				payrollBean.remove(pr);
			}
		}
		payrolls=payrollDAO.findAll();
		for (Payroll pr : payrolls) {
			if ("Hi, I'm a test message. Nice to meet you!".equals(pr.getMessage())) {
				found=true;
				break;
			}
		}
		Assert.assertTrue("Polite message removed!", found==false);
	}
}
