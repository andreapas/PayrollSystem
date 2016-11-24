package it.unipv.payroll.tests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unipv.payroll.controller.UserTransactionsController;
import it.unipv.payroll.dao.UserTransactionsDAO;
import it.unipv.payroll.model.UserTransactions;
import it.unipv.payroll.view.UserTransactionsBean;

@RunWith(Arquillian.class)
public class UserTransactionsTest extends ArquillianTest{

	private static String USER_CODE1= "COD001ABC";
	private static String USER_CODE2= "COD002ABC";
	private static String USER_CODE3= "COD003ABC";
	
	@Inject UserTransactionsBean utBean;
	@Inject UserTransactionsController utController;
	@Inject UserTransactionsDAO utDAO;
	
	
	@After
	public void removeEntries(){
		UserTransactions transaction=new UserTransactions();
		List<UserTransactions> transactions=utDAO.findAll();
		for (int i = 0; i < transactions.size(); i++) {
			if (transactions.get(i).getCode().equals(USER_CODE1)) {
				transaction.setCode(USER_CODE1);
				utDAO.remove(transaction);
			}else if (transactions.get(i).getCode().equals(USER_CODE2)) {
				transaction.setCode(USER_CODE2);
				utDAO.remove(transaction);
			}else if (transactions.get(i).getCode().equals(USER_CODE3)) {
				transaction.setCode(USER_CODE3);
				utDAO.remove(transaction);
			}
		}
	}
	
	@Test
	public void addTaxToEmployee(){
		UserTransactions aTransaction= new UserTransactions();
		aTransaction.setCode(USER_CODE1);
		aTransaction.setFee(50);
		aTransaction.setEarned(1000);
		utBean.setTransaction(aTransaction);
		
		utBean.addTransaction();

		utBean.addFee(50);
		
		List<UserTransactions> allTransactions=utDAO.findAll();
		int totalFee=0;
		for (UserTransactions uts : allTransactions) {
			if (USER_CODE1.equals(uts.getCode())) {
				totalFee=uts.getFee();
			}
		}
		Assert.assertTrue("The total fees are 100 bucks", totalFee==100);
		
		
	}
	
	@Test
	public void addEarnedToEmployee(){
		
		UserTransactions aTransaction= new UserTransactions();
		aTransaction.setCode("COD001ABC");
		aTransaction.setFee(50);
		aTransaction.setEarned(1000);
		utBean.setTransaction(aTransaction);
		
		utBean.addTransaction();

		utBean.addEarned(500);
		
		List<UserTransactions> allTransactions=utDAO.findAll();
		int totalEarned=0;
		for (UserTransactions uts : allTransactions) {
			if (USER_CODE1.equals(uts.getCode())) {
				totalEarned=uts.getEarned();
			}
		}
		Assert.assertTrue("The total earnings are 1500 bucks", totalEarned==1500);
		
	}
	
	@Test
	public void testPayday(){
		UserTransactions firstTransaction= new UserTransactions();
		firstTransaction.setCode(USER_CODE1);
		firstTransaction.setFee(50);
		firstTransaction.setEarned(1000);
		utBean.setTransaction(firstTransaction);
		utBean.addTransaction();
		
		UserTransactions secondTransaction= new UserTransactions();
		secondTransaction.setCode(USER_CODE2);
		secondTransaction.setFee(5000);
		secondTransaction.setEarned(2000);
		utBean.setTransaction(secondTransaction);
		utBean.addTransaction();
		
		UserTransactions thirdTransaction= new UserTransactions();
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
		
		UserTransactions firstTransaction= new UserTransactions();
		firstTransaction.setCode(USER_CODE1);
		firstTransaction.setFee(50);
		firstTransaction.setEarned(1000);
		utBean.setTransaction(firstTransaction);
		utBean.addTransaction();
		
		UserTransactions secondTransaction= new UserTransactions();
		secondTransaction.setCode(USER_CODE2);
		secondTransaction.setFee(5000);
		secondTransaction.setEarned(2000);
		utBean.setTransaction(secondTransaction);
		utBean.addTransaction();
		
		UserTransactions thirdTransaction= new UserTransactions();
		thirdTransaction.setCode(USER_CODE3);
		thirdTransaction.setFee(200);
		thirdTransaction.setEarned(200);
		utBean.setTransaction(thirdTransaction);
		utBean.addTransaction();
		
		utBean.startPayday();
		
		List<UserTransactions> allTransactions=utDAO.findAll();
		
		boolean notNulled=false;
		for (UserTransactions ut : allTransactions) {
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
	

}
