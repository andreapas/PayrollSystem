package it.unipv.payroll.tests;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unipv.payroll.dao.EmployeeTransactionsDAO;
import it.unipv.payroll.dao.TransactionsInfoDAO;
import it.unipv.payroll.model.EmployeeTransactions;
import it.unipv.payroll.model.TransactionsInfo;
import it.unipv.payroll.view.EmployeeTransactionsBean;
import it.unipv.payroll.view.TransactionsInfoBean;

@RunWith(Arquillian.class)
public class EmployeeTransactionsTest extends ArquillianTest{

	private static String USER_CODE1= "COD001ABC";
	private static String USER_CODE2= "COD002ABC";
	private static String USER_CODE3= "COD003ABC";
	
	@Inject EmployeeTransactionsBean utBean;
	@Inject EmployeeTransactionsDAO utDAO;
	
	
	@Inject TransactionsInfoBean tiBean;
	@Inject TransactionsInfoDAO tiDAO;
	
	
	@After
	public void removeEntries(){
		EmployeeTransactions transaction=new EmployeeTransactions();
		List<EmployeeTransactions> transactions=utDAO.findAll();
		for (int i = 0; i < transactions.size(); i++) {
			if (transactions.get(i).getCode().equals(USER_CODE1)) {
				transaction.setCode(USER_CODE1);
				utDAO.remove(transaction.getCode());
			}else if (transactions.get(i).getCode().equals(USER_CODE2)) {
				transaction.setCode(USER_CODE2);
				utDAO.remove(transaction.getCode());
			}else if (transactions.get(i).getCode().equals(USER_CODE3)) {
				transaction.setCode(USER_CODE3);
				utDAO.remove(transaction.getCode());
			}
		}
	}
	
	@Test
	public void addTaxToEmployee(){
		EmployeeTransactions aTransaction= new EmployeeTransactions();
		aTransaction.setCode(USER_CODE1);
		aTransaction.setFee(50);
		aTransaction.setEarned(1000);
		utBean.setTransaction(aTransaction);
		
		utBean.addTransaction();

		utBean.addFee(50, aTransaction.getCode());
		
		List<EmployeeTransactions> allTransactions=utDAO.findAll();
		int totalFee=0;
		for (EmployeeTransactions uts : allTransactions) {
			if (USER_CODE1.equals(uts.getCode())) {
				totalFee=uts.getFee();
			}
		}
		Assert.assertTrue("The total fees are 100 bucks", totalFee==100);
		
		
	}
	
	@Test
	public void addEarnedToEmployee(){
		
		EmployeeTransactions aTransaction= new EmployeeTransactions();
		aTransaction.setCode("COD001ABC");
		aTransaction.setFee(50);
		aTransaction.setEarned(1000);
		utBean.setTransaction(aTransaction);
		
		utBean.addTransaction();

		utBean.addEarned(500, aTransaction.getCode());
		
		List<EmployeeTransactions> allTransactions=utDAO.findAll();
		int totalEarned=0;
		for (EmployeeTransactions uts : allTransactions) {
			if (USER_CODE1.equals(uts.getCode())) {
				totalEarned=uts.getEarned();
			}
		}
		Assert.assertTrue("The total earnings are 1500 bucks", totalEarned==1500);
		
	}
	
	@Test
	public void testPayday(){
		EmployeeTransactions firstTransaction= new EmployeeTransactions();
		firstTransaction.setCode(USER_CODE1);
		firstTransaction.setFee(50);
		firstTransaction.setEarned(1000);
		utBean.setTransaction(firstTransaction);
		utBean.addTransaction();
		
		EmployeeTransactions secondTransaction= new EmployeeTransactions();
		secondTransaction.setCode(USER_CODE2);
		secondTransaction.setFee(5000);
		secondTransaction.setEarned(2000);
		utBean.setTransaction(secondTransaction);
		utBean.addTransaction();
		
		EmployeeTransactions thirdTransaction= new EmployeeTransactions();
		thirdTransaction.setCode(USER_CODE3);
		thirdTransaction.setFee(200);
		thirdTransaction.setEarned(200);
		utBean.setTransaction(thirdTransaction);
		utBean.addTransaction();
		
		HashMap<String, Integer> employeeEarnings=utBean.startPayday();
		
		System.out.println(employeeEarnings.get(USER_CODE1));
		Assert.assertTrue("First employee earned 950 bucks", employeeEarnings.get(USER_CODE1)==950);
		Assert.assertTrue("Second employee earned -3000 bucks", employeeEarnings.get(USER_CODE2)==-3000);
		Assert.assertTrue("Third employee earned no bucks", employeeEarnings.get(USER_CODE3)==0);
		
		
		
		
	}
	
	@Test
	public void verifyCleanUp(){
		
		EmployeeTransactions firstTransaction= new EmployeeTransactions();
		firstTransaction.setCode(USER_CODE1);
		firstTransaction.setFee(50);
		firstTransaction.setEarned(1000);
		utBean.setTransaction(firstTransaction);
		utBean.addTransaction();
		
		EmployeeTransactions secondTransaction= new EmployeeTransactions();
		secondTransaction.setCode(USER_CODE2);
		secondTransaction.setFee(5000);
		secondTransaction.setEarned(2000);
		utBean.setTransaction(secondTransaction);
		utBean.addTransaction();
		
		EmployeeTransactions thirdTransaction= new EmployeeTransactions();
		thirdTransaction.setCode(USER_CODE3);
		thirdTransaction.setFee(200);
		thirdTransaction.setEarned(200);
		utBean.setTransaction(thirdTransaction);
		utBean.addTransaction();
		
		utBean.startPayday();
		
		List<EmployeeTransactions> allTransactions=utDAO.findAll();
		
		boolean notNulled=false;
		for (EmployeeTransactions ut : allTransactions) {
			if(ut.getCode().equals(USER_CODE1)){
				if (ut.getEarned()!=0||ut.getFee()!=0) {
					notNulled=true;
					break;
				}
			}else if(ut.getCode().equals(USER_CODE2)){
				if (ut.getEarned()!=0||ut.getFee()!=0) {
					notNulled=true;
					break;
				}
			}else if(ut.getCode().equals(USER_CODE3)){
				if (ut.getEarned()!=0||ut.getFee()!=0) {
					notNulled=true;
					break;
				}
			}
		}
		Assert.assertTrue("All the emplyees has no fees nor earnings pending", notNulled==false);
		
	}

	@Test
	public void transactionsInfoTest(){
		
		TransactionsInfo ti1 = new TransactionsInfo();
		ti1.setInfo("Transactions info 1");
		ti1.setCode(USER_CODE1);
		tiBean.setTransactionsInfo(ti1);
		tiBean.addTransactionsInfo();
		
		TransactionsInfo ti2 = new TransactionsInfo();
		ti2.setInfo("Transactions info 2");
		ti2.setCode(USER_CODE1);
		tiBean.setTransactionsInfo(ti2);
		tiBean.addTransactionsInfo();
		
		TransactionsInfo ti3 = new TransactionsInfo();
		ti3.setInfo("Transactions info 3");
		ti3.setCode(USER_CODE1);
		tiBean.setTransactionsInfo(ti3);
		tiBean.addTransactionsInfo();

		
		List<TransactionsInfo> allTransactionsInfo = tiDAO.findAll();
		
		int aux = 0;
		for(TransactionsInfo t:allTransactionsInfo){
			
			if(t.getCode().equals(USER_CODE1)){
				aux++;
			}
			
			
		}
		
		Assert.assertTrue("3 Transactions info found!!!",aux==3);
		
		List<TransactionsInfo>list= tiDAO.findAll();
		for (TransactionsInfo transactionsInfo : list) {
			if(transactionsInfo.getInfo().equals(ti1.getInfo())){
				tiBean.removeTransactionInfo(transactionsInfo);
			}else if(transactionsInfo.getInfo().equals(ti2.getInfo())){
				tiBean.removeTransactionInfo(transactionsInfo);
			}else if(transactionsInfo.getInfo().equals(ti3.getInfo())){
				tiBean.removeTransactionInfo(transactionsInfo);
			} 
		}
		
		List<TransactionsInfo> lastList=tiDAO.findAll();
		int tot=0;
		for (TransactionsInfo transactionsInfo : lastList) {
			if (transactionsInfo.getCode().equals(USER_CODE1)) {
				tot++;
			}
		}
		
		Assert.assertTrue("All has been deleted", tot==0);
		
	}

}
