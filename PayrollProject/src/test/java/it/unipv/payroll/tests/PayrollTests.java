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
import it.unipv.payroll.view.PayrollBean;

@RunWith(Arquillian.class)
public class PayrollTests extends ArquillianTest{

	@Inject PayrollBean payrollBean;
	@Inject PayrollDAO payrollDAO;
	
	@Before
	public void cleanup(){
		List<Payroll> payrolls=payrollDAO.findAll();
		boolean found=false;
		for (Payroll pr : payrolls) {
			if ("Hi, I'm a test message. Nice to meet you!".equals(pr.getMessage())) {
				payrollBean.remove(pr);
			}
		}
	}
	
	@Test
	public void testRemove(){
		List<Payroll> payrolls=payrollDAO.findAll();
		boolean found=false;
		for (Payroll pr : payrolls) {
			if ("Hi, I'm a test message. Nice to meet you!".equals(pr.getMessage())) {
				found=true;
				break;
			}
		}
		Assert.assertTrue("Polite message found!", found==false);
	}
	
	@Test
	public void test() {
		// Set an element in the bean
		Payroll aPayroll= new Payroll();
		aPayroll.setMessage("Hi, I'm a test message. Nice to meet you!");
		payrollBean.setPayroll(aPayroll);
		
		
		payrollBean.addMessage();
		
		List<Payroll> payrolls=payrollDAO.findAll();
		boolean found=false;
		for (Payroll pr : payrolls) {
			if ("Hi, I'm a test message. Nice to meet you!".equals(pr.getMessage())) {
				found=true;
				break;
			}
		}
		Assert.assertTrue("Polite message found!", found);
				
	}

}
