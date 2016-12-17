package it.unipv.payroll.tests;

import java.util.Date;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unipv.payroll.controller.TimeCardController;
import it.unipv.payroll.model.TimeCard;
import it.unipv.payroll.view.TimeCardBean;

@RunWith(Arquillian.class)
public class TimeCardTest extends ArquillianTest {

	private TimeCard aTimeCard;
	@Inject TimeCardBean tcBean;
	@Inject TimeCardController tcController;
	
	@Test
	public void testPostTimeCard() {
		aTimeCard= new TimeCard();
		aTimeCard.setEmployeeCode("il bello di casa");
		aTimeCard.setData((new Date()).getTime());;
		aTimeCard.setHoursWorked(10);
		aTimeCard.setPostId(123456789);
		tcBean.setTimeCard(aTimeCard);
		tcBean.postTimeCard();
		
		boolean addedAndFinded=false;
		TimeCard returnedOne=tcBean.findCardById(123456789);
		if(returnedOne.getPostId()==aTimeCard.getPostId()){
			addedAndFinded=true;
			
		}
		Assert.assertTrue("Time Card successfully added and finded",addedAndFinded);
		tcController.remove(123456789);

	}

}
