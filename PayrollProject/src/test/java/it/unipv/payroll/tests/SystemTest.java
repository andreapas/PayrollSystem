package it.unipv.payroll.tests;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.controller.UnionsController;
import it.unipv.payroll.dao.AddressDAO;
import it.unipv.payroll.dao.EmployeeDAO;
import it.unipv.payroll.dao.SessionManagementDAO;
import it.unipv.payroll.dao.TransactionsDAO;
import it.unipv.payroll.model.Address;
import it.unipv.payroll.model.Credentials;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FullTimeEmployee;
import it.unipv.payroll.model.PartTimeEmployee;
import it.unipv.payroll.model.Transactions;
import it.unipv.payroll.model.Union;
import it.unipv.payroll.utils.AutoPayday;
import it.unipv.payroll.utils.UnionConverter;
import it.unipv.payroll.view.EmployeeBean;
import it.unipv.payroll.view.SessionManagementBean;
import it.unipv.payroll.view.TransactionsBean;
import it.unipv.payroll.view.UnionsBean;

@RunWith(Arquillian.class)
public class SystemTest extends ArquillianTest {

	private static String USER2_COD = "wow987654321";
	private static String USER1_COD = "ABC001DEF";
	private static String USER1_NAME = "Mario";
	private static String USER1_SURNAME = "Rossi";
	private static String USER1_EMAIL = "mario.rossi@mariorossi.it";
	private static String USER1_EMAIL_EDITED = "mario.rossini@mariorossini.it";
	private static Union USER1_UNION;
	private static Union USER1_UNION_EDITED;
	private static String PAYMENT_METHOD1 = "Bank account";
	private static String PAYMENT_METHOD2 = "By hand";
	private static PartTimeEmployee anEmployee;
	private static FullTimeEmployee anotherEmployee;
	private static Address ADDRESS1;
	private static Address ADDRESS2;
	
	@Inject	EmployeeBean emBean;
	@Inject	EmployeeController emController;
	@Inject	EmployeeDAO emDAO;
	
	@Inject AddressDAO addrDAO;
	
	@Inject	UnionsBean unBean;
	@Inject	UnionsController unController;	
	
	
	@Inject TransactionsBean tBean;
	@Inject TransactionsDAO tDAO;
	
	@Inject SessionManagementBean sesmanBean;
	@Inject SessionManagementDAO sesmanDAO;
	
	@Inject AutoPayday payer;
	
	@Before
	public void setUp(){
		
		ADDRESS1=new Address();
		ADDRESS1.setCap(12345);
		ADDRESS1.setCode(USER1_COD);
		ADDRESS1.setDistrictCode("PV");
		ADDRESS1.setMunicipality("Pavia");
		ADDRESS1.setNumber(0);
		ADDRESS1.setStreet("via dei matti");
		
		ADDRESS2=new Address();
		ADDRESS2.setCap(12345);
		ADDRESS2.setCode(USER2_COD);
		ADDRESS2.setDistrictCode("RM");
		ADDRESS2.setMunicipality("Roma");
		ADDRESS2.setNumber(9);
		ADDRESS2.setStreet("sani dei via");
		
		USER1_UNION= new Union();
		USER1_UNION.setUnionName("union 1");
		USER1_UNION.setWeeklyRate(9000);
		unBean.setUnion(USER1_UNION);
		unBean.addUnion();
		USER1_UNION_EDITED= new Union();
		USER1_UNION_EDITED.setUnionName("union 2");
		USER1_UNION_EDITED.setWeeklyRate(5000);
		unBean.setUnion(USER1_UNION_EDITED);
		unBean.addUnion();
		
		anEmployee = new PartTimeEmployee();
		anEmployee.setCode(USER1_COD);
		anEmployee.setName(USER1_NAME);
		anEmployee.setSurname(USER1_SURNAME);
		anEmployee.setEmail(USER1_EMAIL);
		anEmployee.setUnion(USER1_UNION);
		anEmployee.setPayment_method(PAYMENT_METHOD1);
		anEmployee.setAddress(ADDRESS1);
		anEmployee.setHourlyRate((float)6.25);
		
		anotherEmployee= new FullTimeEmployee();
		anotherEmployee.setCode(USER2_COD);
		anotherEmployee.setName(USER1_NAME);
		anotherEmployee.setSurname(USER1_SURNAME);
		anotherEmployee.setEmail(USER1_EMAIL);
		anotherEmployee.setUnion(USER1_UNION);
		anotherEmployee.setPayment_method(PAYMENT_METHOD1);
		anotherEmployee.setAddress(ADDRESS2);
		anotherEmployee.setCommissionRate(50);
		anotherEmployee.setSalary(1200);
	}
	
	@After
	public void cleanup(){
		if(emController.find(USER1_COD)!=null){
			emBean.fireEmployee(anEmployee.getCode());
		}
		if(emController.find(USER2_COD)!=null){
			emBean.fireEmployee(anotherEmployee.getCode());
		}
		unBean.setFireUnionName(USER1_UNION.getUnionName());
		unBean.removeUnion();
		unBean.setFireUnionName(USER1_UNION_EDITED.getUnionName());
		unBean.removeUnion();
		
		
		
	}
	@Test
	public void testHireUser() {
		
		emBean.setPartTimeEmployee(anEmployee);
		emBean.hirePartTimeEmployee();
		emBean.setRadioVal(emBean.getOptionsList().get(0));

		List<Employee> employees = emDAO.findAll();
		boolean isPresent = false;
		for (Employee em : employees) {
			if (em.getCode().equals(USER1_COD)) {
				isPresent = true;
				break;
			}
			
		}
		Assert.assertTrue("Employee hired successfully!", isPresent);
		Assert.assertEquals(emBean.getOptionsList().get(0), emBean.getRadioVal());
		
	}

	@Test
	public void testFireUser() {

		emBean.setPartTimeEmployee(anEmployee);
		emBean.hirePartTimeEmployee();
		tBean.setLoggedEmployee(anEmployee);
		Transactions transaction= new Transactions();
		transaction.setDate(new Date());
		transaction.setInfo("Sale ID=12345 test");
		transaction.setAmount((float)55.55);
		tBean.setTransaction(transaction);
		tBean.addServiceCharge();
//		emBean.setFireCode(anEmployee.getCode());
		emBean.fireEmployee(anEmployee.getCode());

		List<Employee> employees = emDAO.findAll();
		boolean isPresent = false;
		for (Employee em : employees) {
			if (em.getCode().equals(USER1_COD)) {
				isPresent = true;
				break;
			}
		}

		Assert.assertTrue("Employee fired successfully!", !isPresent);
		Assert.assertTrue("Address of the employee removed!", addrDAO.find(anEmployee.getCode())==null);
		Assert.assertTrue("Transactions of the employee removed!", tDAO.find(transaction.getId())==null);
		
	}

	@Test
	public void editUser() {
		anEmployee.setUnion(USER1_UNION);
		
		emBean.setPartTimeEmployee(anEmployee);
		emBean.hirePartTimeEmployee();
		

//		emBean.setLoggedUser(emBean.findEmployeeByCode(anEmployee.getCode()));;
		anEmployee.setEmail(USER1_EMAIL_EDITED);
		anEmployee.setUnion(USER1_UNION_EDITED);
		anEmployee.setPayment_method(PAYMENT_METHOD2);
		emBean.setGenericEmployee(anEmployee);
		emBean.updateInfo();

		
		Assert.assertEquals(PAYMENT_METHOD2, emBean.findEmployeeByCode(anEmployee.getCode()).getPayment_method());
		Assert.assertEquals(USER1_EMAIL_EDITED, emBean.findEmployeeByCode(anEmployee.getCode()).getEmail());
		Assert.assertEquals(USER1_UNION_EDITED.getUnionName(), emBean.findEmployeeByCode(anEmployee.getCode()).getUnion().getUnionName());
		Assert.assertEquals(anEmployee.getCode(), emBean.getGenericEmployee().getCode());
	}

	@Test
	public void autoMapUnion() {
		emBean.setPartTimeEmployee(anEmployee);
		emBean.hirePartTimeEmployee();

		boolean mapWorking=false;
		if(emBean.findEmployeeByCode(anEmployee.getCode()).getUnion().getUnionName().equals(USER1_UNION.getUnionName())){
			mapWorking=true;
		}
		
		
		
		emBean.setFullTimeEmployee(anotherEmployee);
		emBean.hireFullTimeEmployee();
		
		boolean inverseMapWorking=false;
		List<Employee> associates= unController.findUnion(USER1_UNION.getUnionName()).getAssociates();
		for (Employee employee : associates) {
			if(employee.getUnion().getUnionName().equals(USER1_COD)||employee.getUnion().getUnionName().equals(USER2_COD)){
				inverseMapWorking=true;
			}
			if(associates.size()==2){
				inverseMapWorking=true;
			}
		}
		
		Assert.assertTrue("The User has been successfully mapped into the union", mapWorking);
		Assert.assertTrue("The Union has mapped correctly with the users", inverseMapWorking);

	}
	
	@Test
	public void testUnions() {
		
		Union union1 = new Union();
		union1.setUnionName("unionTest");
		union1.setWeeklyRate((float)10.5);
		unBean.setUnion(union1);
		unBean.addUnion();

		Union union = new Union();
		union.setUnionName("unionTest2");
		union.setWeeklyRate((float)0.52);
		unBean.setUnion(union);
		unBean.addUnion();
		
		Assert.assertTrue("Set union is working properly", union.equals(unBean.getUnion()));
		
		List<Union> unions = unBean.getUnionsList();
		int flag = 0;
		boolean isEqualsWorking=false;
		for (Union u : unions) {
			if (u.equals(union)|| u.equals(union1)) {
				if (u.getUnionName().equals(union.getUnionName())||u.getUnionName().equals(union1.getUnionName())) {
					isEqualsWorking=true;
					flag++;
				}
			}
		}
		Assert.assertTrue("2 unions added!", flag == 2);
		Assert.assertTrue("Override of equals is working", isEqualsWorking);
		
		Union tmpUnion=unController.findUnion(union1.getUnionName());
		Assert.assertTrue("findUnion is working properly", tmpUnion.equals(union1));
		
		
		
		union1.setWeeklyRate((float)1.50);
		unBean.setUnion(union1);
		unBean.updateUnion();
		
		Assert.assertTrue("Update successfully completed",unController.findUnion(union1.getUnionName()).equals(union1));
		
		unBean.setFireUnionName(union.getUnionName());
		unBean.removeUnion();
		unBean.setFireUnionName(union1.getUnionName());
		unBean.removeUnion();
		
		unions = unBean.getUnionsList();
		boolean hasBeenFired=true;
		for (Union u : unions) {
			if (u.equals(union)|| u.equals(union1)) {
				hasBeenFired=false;
			}
		}
		Assert.assertTrue("Fire union is working correctly", hasBeenFired);
		Assert.assertTrue("Fire union name has been correctly cleared", unBean.getFireUnionName().equals(""));
		
	}
	
	@Test
	public void testTransactions() {
		
		emBean.setPartTimeEmployee(anEmployee);
		emBean.hirePartTimeEmployee();
		emBean.setFullTimeEmployee(anotherEmployee);
		emBean.hireFullTimeEmployee();
		
		Transactions aTransaction = new Transactions();
		tBean.setHoursAmount(10);
		tBean.setTransaction(aTransaction);
		tBean.setLoggedEmployee(anEmployee);
		tBean.addHours();

		tBean.setHoursAmount(7);
		tBean.setTransaction(aTransaction);
		tBean.setLoggedEmployee(anEmployee);
		tBean.addHours();
		
		Transactions anotherTransaction = new Transactions();
		anotherTransaction.setDate(new Date());
		anotherTransaction.setInfo("Sale ID=12345 test");
		anotherTransaction.setAmount((float)55.55);
		tBean.setId(12345);
		tBean.setTransaction(anotherTransaction);
		tBean.setLoggedEmployee(anotherEmployee);
		tBean.addSaleRecipt();
		
		List<Transactions> allTransactions = tBean.getAllTransactions();
		int trans1 = 0;
		int trans2 = 0;
		for (Transactions t : allTransactions) {
			if (USER1_COD.equals(t.getEmployee().getCode())) {
				trans1++;
			}
			if (USER2_COD.equals(t.getEmployee().getCode())) {
				trans2++;
			}
		}
		Assert.assertEquals(3, trans1+trans2);
		
		List<Employee> employeesToAddCharge=new ArrayList<Employee>();
		employeesToAddCharge.add(anEmployee);
		employeesToAddCharge.add(anotherEmployee);
		tBean.setEmployeeList(employeesToAddCharge);
		
		Transactions charge= new Transactions();
		charge.setAmount(100);
		charge.setDate(new Date());
		charge.setInfo("test charge");
		
		tBean.setTransaction(charge);
		tBean.addServiceCharge();
		allTransactions = tBean.getAllTransactions();
		int numCharges=0;
		int employees=0;
		
		for (Transactions t : allTransactions) {
			if(t.getInfo().equals("test charge")){
				numCharges++;
				if(t.getEmployee().getCode().equals(anEmployee.getCode())||t.getEmployee().getCode().equals(anotherEmployee.getCode())){
					employees++;
				}
			}
			
		}
		
		Assert.assertTrue("Two total charges added",numCharges==2);
		Assert.assertTrue("Charges added to two employees",employees==2);
		
		
		for (Transactions t : allTransactions) {
			if (t.getEmployee().getCode().equals(USER1_COD)) {
				tDAO.remove(t.getId());
			}else if (t.getEmployee().getCode().equals(USER2_COD)) {
				tDAO.remove(t.getId());
			}
		}
		
	}
	
	@Test
	public void testChangePassword() throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("SHA-256");

		emBean.setFullTimeEmployee(anotherEmployee);
		emBean.hireFullTimeEmployee();
		Credentials newLoginCredentials= new Credentials();
		newLoginCredentials.setCode(anotherEmployee.getCode());
		newLoginCredentials.setPassword(Base64.getEncoder().encodeToString(md.digest("basePassword".getBytes())));
		sesmanDAO.update(newLoginCredentials);
		newLoginCredentials.setPassword(Base64.getEncoder().encodeToString(md.digest("newPassword".getBytes())));
		sesmanBean.setNewLoginCredentials(newLoginCredentials);
		sesmanBean.getOldLoginCredentials().setCode(anotherEmployee.getCode());
		sesmanBean.getOldLoginCredentials().setPassword("basePassword");
		sesmanBean.modifyPassword();
		
		Assert.assertEquals(sesmanBean.getNewLoginCredentials().getCode(), newLoginCredentials.getCode());
		Assert.assertEquals(sesmanBean.getNewLoginCredentials().getPassword(), newLoginCredentials.getPassword());
		
		Assert.assertEquals(sesmanDAO.find(anotherEmployee.getCode()).getPassword(), newLoginCredentials.getPassword());
	}
	
	@Test
	public void testLogoutReturnPage(){
		String returnPage="/index.xhtml?faces-redirect=true";
		String returnedPage=sesmanBean.logout();
		Assert.assertEquals(returnPage, returnedPage);
		
	}
	
	@Test
	public void testPayday(){
		
		Union union1 = new Union();
		union1.setUnionName("unionTest");
		union1.setWeeklyRate(12);
		unBean.setUnion(union1);
		unBean.addUnion();

		Union union = new Union();
		union.setUnionName("unionTest2");
		union.setWeeklyRate(15);
		unBean.setUnion(union);
		unBean.addUnion();
		
		anEmployee.setUnion(union);
		anotherEmployee.setUnion(union1);
		emBean.setPartTimeEmployee(anEmployee);
		emBean.hirePartTimeEmployee();
		emBean.setFullTimeEmployee(anotherEmployee);
		emBean.hireFullTimeEmployee();

		Transactions aTransaction = new Transactions();
		tBean.setHoursAmount(8);
		tBean.setTransaction(aTransaction);
		tBean.setLoggedEmployee(anEmployee);
		tBean.addHours();
		tBean.setTransaction(aTransaction);
		tBean.setLoggedEmployee(anEmployee);
		tBean.addHours();
		tBean.setTransaction(aTransaction);
		tBean.setLoggedEmployee(anEmployee);
		tBean.addHours();
		tBean.setTransaction(aTransaction);
		tBean.setLoggedEmployee(anEmployee);
		tBean.addHours();
		tBean.setTransaction(aTransaction);
		tBean.setLoggedEmployee(anEmployee);
		tBean.addHours();
		
		Transactions anotherTransaction = new Transactions();
		anotherTransaction.setDate(new Date());
		anotherTransaction.setAmount(800);
		tBean.setId(12345);
		tBean.setTransaction(anotherTransaction);
		tBean.setLoggedEmployee(anotherEmployee);
		tBean.addSaleRecipt();
		
		HashMap<String, Float>weeklyEarns=payer.weeklyPay();
		HashMap<String, Float>monthlyEarns=payer.monthlyPay();
		
		Assert.assertEquals(235,weeklyEarns.get(anEmployee.getCode()), 0.001);
		Assert.assertEquals(1588, monthlyEarns.get(anotherEmployee.getCode()), 0.001);
		
		emBean.fireEmployee(anEmployee.getCode());
		emBean.fireEmployee(anotherEmployee.getCode());
		unBean.setFireUnionName(union.getUnionName());
		unBean.removeUnion();
		unBean.setFireUnionName(union1.getUnionName());
		unBean.removeUnion();
		
	}
	
	@Inject
	UnionConverter unConv;
	
	@Test
	public void unionConverterTest(){
		
		Object un=unConv.getAsObject(null, null, USER1_UNION.getUnionName());
		Assert.assertEquals((Object)USER1_UNION, un);
		String un1=unConv.getAsString(null, null, USER1_UNION);
		Assert.assertEquals(USER1_UNION.getUnionName(), un1);
		
	}
}
