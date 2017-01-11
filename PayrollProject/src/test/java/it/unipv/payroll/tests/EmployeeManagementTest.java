package it.unipv.payroll.tests;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unipv.payroll.controller.AddressController;
import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.controller.UnionsController;
import it.unipv.payroll.dao.EmployeeDAO;
import it.unipv.payroll.model.Address;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FullTimeEmployee;
import it.unipv.payroll.model.PartTimeEmployee;
import it.unipv.payroll.model.Union;
import it.unipv.payroll.view.EmployeeBean;
import it.unipv.payroll.view.UnionsBean;

@RunWith(Arquillian.class)
public class EmployeeManagementTest extends ArquillianTest {

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
	private static String MonthlyRole= "Monthly";
	private static String WeeklyRole= "Weekly";
	private static Address ADDRESS1;
	private static Address ADDRESS2;
	
	@Inject	EmployeeBean emBean;
	@Inject	EmployeeController emController;
	@Inject	EmployeeDAO emDAO;
	
	@Inject	UnionsBean unBean;
	@Inject	UnionsController unController;	
	
	@Before
	public void setUp(){
		
		ADDRESS1=new Address();
		ADDRESS1.setCap(12345);
		ADDRESS1.setCode(USER1_COD);
		ADDRESS1.setDistrictCode("PV");
		ADDRESS1.setEmployee(null);
		ADDRESS1.setMunicipality("Pavia");
		ADDRESS1.setNumber(0);
		ADDRESS1.setStreet("via dei matti");
		
		ADDRESS2=new Address();
		ADDRESS2.setCap(12345);
		ADDRESS2.setCode(USER2_COD);
		ADDRESS2.setDistrictCode("RM");
		ADDRESS2.setEmployee(null);
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
		anEmployee.setRole(WeeklyRole);
		anEmployee.setAddress(ADDRESS1);
		
		anotherEmployee= new FullTimeEmployee();
		anotherEmployee.setCode(USER2_COD);
		anotherEmployee.setName(USER1_NAME);
		anotherEmployee.setSurname(USER1_SURNAME);
		anotherEmployee.setEmail(USER1_EMAIL);
		anotherEmployee.setUnion(USER1_UNION);
		anotherEmployee.setPayment_method(PAYMENT_METHOD1);
		anotherEmployee.setRole(MonthlyRole);
		anotherEmployee.setAddress(ADDRESS2);
	}
	
	@After
	public void cleanup(){
		if(emController.find(USER1_COD)!=null){
			emBean.setFireCode(anEmployee.getCode());
			emBean.fireEmployee();
		}
		if(emController.find(USER2_COD)!=null){
			emBean.setFireCode(anotherEmployee.getCode());
			emBean.fireEmployee();
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

		List<Employee> employees = emDAO.findAll();
		boolean isPresent = false;
		for (Employee em : employees) {
			if (em.getCode().equals(USER1_COD)) {
				isPresent = true;
				break;
			}
			
		}

		Assert.assertTrue("Employee hired successfully!", isPresent);
	}

	@Test
	public void testFireUser() {

		emBean.setPartTimeEmployee(anEmployee);
		emBean.hirePartTimeEmployee();
		emBean.setFireCode(anEmployee.getCode());
		emBean.fireEmployee();

		List<Employee> employees = emDAO.findAll();
		boolean isPresent = false;
		for (Employee em : employees) {
			if (em.getCode().equals(USER1_COD)) {
				isPresent = true;
				break;
			}
		}

		Assert.assertTrue("Employee fired successfully!", !isPresent);
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
		emBean.updateInfo(anEmployee);

		
		Assert.assertEquals(PAYMENT_METHOD2, emBean.findEmployeeByCode(anEmployee.getCode()).getPayment_method());
		Assert.assertEquals(USER1_EMAIL_EDITED, emBean.findEmployeeByCode(anEmployee.getCode()).getEmail());
		Assert.assertEquals(USER1_UNION_EDITED.getUnionName(), emBean.findEmployeeByCode(anEmployee.getCode()).getUnion().getUnionName());
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
		union1.setWeeklyRate(10.5);
		unBean.setUnion(union1);
		unBean.addUnion();

		Union union = new Union();
		union.setUnionName("unionTest2");
		union.setWeeklyRate(0.52);
		unBean.setUnion(union);
		unBean.addUnion();
		
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
		
		
	}
	
}
