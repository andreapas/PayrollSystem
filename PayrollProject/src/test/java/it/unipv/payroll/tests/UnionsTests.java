package it.unipv.payroll.tests;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unipv.payroll.dao.UnionsDAO;
import it.unipv.payroll.model.Payroll;
import it.unipv.payroll.model.Union;
import it.unipv.payroll.view.UnionsBean;

@RunWith(Arquillian.class)
public class UnionsTests extends ArquillianTest {

	@Inject UnionsBean unionsBean;
	@Inject UnionsDAO unionsDao;
	
	@Before
	public void clean(){
		List<Union> unions = unionsDao.getUnions();
		boolean found=false;
		for (Union u : unions) {
			if (u.getUnionName() != null || u.getUnionFee() > -1) {
				unionsDao.remove(u);
			}
		}
	}
	
	@Test
	public void testUnions() {
		
		Union union = new Union();
		union.setUnion("CIGL", 39.99);
		unionsBean.setUnion(union);
		
		unionsBean.add();
		
		union.setUnion("Cobas", 29.50);
		unionsBean.setUnion(union);
		
		unionsBean.add();
		
		List<Union> unions = unionsDao.getUnions();
		int flag = 0;
		for (Union u : unions) {
			if (u.getUnionName() != null && u.getUnionFee() != -1) {
				flag++;
			}
		}
		Assert.assertTrue("2 unions added!", flag == 2);
	}
}
