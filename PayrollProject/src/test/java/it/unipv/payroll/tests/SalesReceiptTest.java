package it.unipv.payroll.tests;

import java.util.Date;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unipv.payroll.controller.SalesReceiptController;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FlatEmployee;
import it.unipv.payroll.model.SalesReceipt;
import it.unipv.payroll.view.SalesReceiptBean;


@RunWith(Arquillian.class)
public class SalesReceiptTest extends ArquillianTest {

	private SalesReceipt salesReceipt;
	private Employee anEmployee;
	
	@Inject SalesReceiptBean srBean;
	@Inject SalesReceiptController srController;
	
	@Test
	public void testPostSalesReceipt() {
		salesReceipt = new SalesReceipt();
		anEmployee = new FlatEmployee();
		anEmployee.setCode("123456789");
		anEmployee.setEmail("payrollproject@se.com");
		anEmployee.setName("Jorge");
		anEmployee.setSurname("Garcia");
		anEmployee.setPayment_method("Bank account");
		salesReceipt.setEmployee(anEmployee);
		salesReceipt.setDate((new Date()).getTime());
		salesReceipt.setAmount(1500);
		salesReceipt.setPostId(666666111);
		srBean.setSalesReceipt(salesReceipt);
		srBean.postSalesReceipt();

		boolean b = false;
		SalesReceipt returnedOne = srBean.findSalesReceiptById(666666111);
		if (returnedOne.getPostId() == salesReceipt.getPostId()) {
			b = true;

		}
		Assert.assertTrue("Sales receipt found", b);
		srController.remove(666666111);

	}

}
