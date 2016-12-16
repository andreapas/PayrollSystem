package it.unipv.payroll.tests;

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
import it.unipv.payroll.dao.EmployeeDAO;
import it.unipv.payroll.model.Employee;
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
	private static Employee anEmployee;
	private static Employee anotherEmployee;
	@Inject
	EmployeeBean emBean;
	@Inject
	UnionsBean unBean;
	@Inject
	EmployeeController emController;
	@Inject
	UnionsController unController;	
	@Inject
	EmployeeDAO emDAO;

	
	@Before
	public void setUp(){
		USER1_UNION= new Union();
		USER1_UNION.setUnionName("union 1");
		USER1_UNION.setUnionFee(9000);
		unBean.setUnion(USER1_UNION);
		unBean.addUnion();
		USER1_UNION_EDITED= new Union();
		USER1_UNION_EDITED.setUnionName("union 2");
		USER1_UNION_EDITED.setUnionFee(5000);
		unBean.setUnion(USER1_UNION_EDITED);
		unBean.addUnion();
		
		anEmployee = new Employee();
		anEmployee.setCode(USER1_COD);
		anEmployee.setName(USER1_NAME);
		anEmployee.setSurname(USER1_SURNAME);
		anEmployee.setEmail(USER1_EMAIL);
		anEmployee.setUnion(USER1_UNION);
		anEmployee.setPayment_method(PAYMENT_METHOD1);
		
		anotherEmployee= new Employee();
		anotherEmployee.setCode(USER2_COD);
		anotherEmployee.setName(USER1_NAME);
		anotherEmployee.setSurname(USER1_SURNAME);
		anotherEmployee.setEmail(USER1_EMAIL);
		anotherEmployee.setUnion(USER1_UNION);
		anotherEmployee.setPayment_method(PAYMENT_METHOD1);
	}
	
	@After
	public void cleanup(){
		if(emController.find(USER1_COD)!=null){
			emBean.setEmployee(anEmployee);
			emBean.fireEmployee();
		}
		if(emController.find(USER2_COD)!=null){
			emBean.setEmployee(anotherEmployee);
			emBean.fireEmployee();
		}
		unBean.setUnion(USER1_UNION);
		unBean.removeUnion(USER1_UNION);
		unBean.setUnion(USER1_UNION_EDITED);
		unBean.removeUnion(USER1_UNION_EDITED);
		
		
	}
	@Test
	public void testHireUser() {
		
		emBean.setEmployee(anEmployee);
		emBean.hireEmployee();

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

		emBean.setEmployee(anEmployee);
		emBean.hireEmployee();
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
		
		emBean.setEmployee(anEmployee);
		emBean.hireEmployee();

		emBean.editEmail(USER1_EMAIL_EDITED);
		emBean.editUnion(USER1_UNION_EDITED);

		List<Employee> employees = emDAO.findAll();
		boolean isEmailEdited = false;
		boolean isUnionEdited = false;
		for (Employee em : employees) {
			if (em.getCode().equals(USER1_COD)) {
				if (em.getEmail().equals(USER1_EMAIL_EDITED)) {
					isEmailEdited = true;
				}
				if (em.getUnion().getUnionName().equals(USER1_UNION_EDITED.getUnionName())) {
					isUnionEdited = true;
				}
				break;
			}
		}

		Assert.assertTrue("Employee email edited successfully!", isEmailEdited);
		Assert.assertTrue("Employee union edited successfully!", isUnionEdited);

		emBean.fireEmployee();
	}

	@Test
	public void testEditPaymentMethod() {

		emBean.setEmployee(anEmployee);
		emBean.hireEmployee();

		emBean.editPaymentMethod(PAYMENT_METHOD2);
		List<Employee> employees = emDAO.findAll();
		boolean isPaymentMethodEdited = false;
		for (Employee e : employees) {
			if (e.getCode().equals(USER1_COD)) {

				if (e.getPayment_method().equals(PAYMENT_METHOD2)) {
					isPaymentMethodEdited = true;
				}

			}
			break;

		}

		Assert.assertTrue("The Payment Method has been changed successfully", isPaymentMethodEdited);

	}
	@Test
	public void autoMapUnion() {
		emBean.setEmployee(anEmployee);
		emBean.hireEmployee();

		boolean mapWorking=false;
		if(emBean.getEmployee().getUnion().getUnionName().equals(USER1_UNION.getUnionName())){
			mapWorking=true;
		}
		
		
		
		emBean.setEmployee(anotherEmployee);
		emBean.hireEmployee();
		
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
}
