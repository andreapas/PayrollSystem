package it.unipv.payroll.tests;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unipv.payroll.dao.UnionsDAO;
import it.unipv.payroll.model.Union;
import it.unipv.payroll.view.UnionsBean;

@RunWith(Arquillian.class)
public class UnionsTests extends ArquillianTest {

	private static double COBAS_TAX = 29.50;
	private static double CIGL_TAX = 39.99;
	private static String COBAS = "Cobas";
	private static String CIGL = "CIGL";
	@Inject UnionsBean unionsBean;
	@Inject UnionsDAO unionsDao;
	
	@After
	public void clean(){
		List<Union> unions = unionsDao.findAll();
		for (Union u : unions) {
			if (u.getUnionName().equals(CIGL)||u.getUnionName().equals(COBAS)) {
				unionsDao.remove(u.getUnionName());
			}
		}
	}
	
	@Test
	public void testUnions() {
		
		Union union = new Union();
		union.setUnionName(CIGL);
		union.setWeeklyRate(CIGL_TAX);
		unionsBean.setUnion(union);
		
		unionsBean.addUnion();
		union.setUnionName(COBAS);
		union.setWeeklyRate(COBAS_TAX);
		unionsBean.setUnion(union);
		
		unionsBean.addUnion();
		
		List<Union> unions = unionsDao.findAll();
		int flag = 0;
		for (Union u : unions) {
			if (u.getUnionName() != null && u.getWeeklyRate() != -1) {
				flag++;
			}
		}
		Assert.assertTrue("2 unions added!", flag == 2);
	}
}
