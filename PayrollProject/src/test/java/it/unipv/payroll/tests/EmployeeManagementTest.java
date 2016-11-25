package it.unipv.payroll.tests;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.dao.EmployeeDAO;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.view.EmployeeBean;


@RunWith(Arquillian.class)
public class EmployeeManagementTest extends ArquillianTest{

	private static String USER1_COD= "ABC001DEF";
	private static String USER1_NAME= "Mario";
	private static String USER1_SURNAME= "Rossi";
	private static String USER1_EMAIL= "mario.rossi@mariorossi.it";
	private static String USER1_EMAIL_EDITED= "mario.rossini@mariorossini.it";
	private static String USER1_UNION= "Cigl";
	private static String USER1_UNION_EDITED= "Uil";

	@Inject EmployeeBean emBean;
	@Inject EmployeeController emController;
	@Inject EmployeeDAO emDAO;
	
	
	
	@Test
	public void testHireUser(){
		
		Employee anEmployee= new Employee();
		anEmployee.setCode(USER1_COD);
		anEmployee.setName(USER1_NAME);
		anEmployee.setSurname(USER1_SURNAME);
		anEmployee.setEmail(USER1_EMAIL);
		anEmployee.setUnion_Name(USER1_UNION);
		
		emBean.setEmployee(anEmployee);
		emBean.hireEmployee();
		
		List<Employee> employees=emDAO.findAll();
		boolean isPresent=false;
		for (Employee em : employees) {
			if (em.getCode().equals(USER1_COD)) {
				isPresent=true;
				break;
			}
		}
		
		Assert.assertTrue("Employee hired successfully!", isPresent);
	}
	
	@Test
	public void testFireUser(){
		
		Employee anEmployee= new Employee();
		anEmployee.setCode(USER1_COD);
		anEmployee.setName(USER1_NAME);
		anEmployee.setSurname(USER1_SURNAME);
		anEmployee.setEmail(USER1_EMAIL);
		anEmployee.setUnion_Name(USER1_UNION);
		
		emBean.setEmployee(anEmployee);
		emBean.fireEmployee();
		
		List<Employee> employees=emDAO.findAll();
		boolean isPresent=false;
		for (Employee em : employees) {
			if (em.getCode().equals(USER1_COD)) {
				isPresent=true;
				break;
			}
		}
		
		Assert.assertTrue("Employee hired successfully!", !isPresent);
	}
	
	@Test
	public void editUser(){
		
		Employee anEmployee= new Employee();
		anEmployee.setCode(USER1_COD);
		anEmployee.setName(USER1_NAME);
		anEmployee.setSurname(USER1_SURNAME);
		anEmployee.setEmail(USER1_EMAIL);
		anEmployee.setUnion_Name(USER1_UNION);
		
		emBean.setEmployee(anEmployee);
		emBean.hireEmployee();
		
		emBean.editEmail(USER1_EMAIL_EDITED);
		emBean.editUnion(USER1_UNION_EDITED);
		
		List<Employee> employees=emDAO.findAll();
		boolean isEmailEdited=false;
		boolean isUnionEdited=false;
		for (Employee em : employees) {
			if (em.getCode().equals(USER1_COD)) {
				if(em.getEmail().equals(USER1_EMAIL_EDITED)){
					isEmailEdited=true;
				}
				if(em.getUnion_Name().equals(USER1_UNION_EDITED)){
					isUnionEdited=true;
				}
				break;
			}
		}
		
		Assert.assertTrue("Employee email edited successfully!", isEmailEdited);
		Assert.assertTrue("Employee union edited successfully!", isUnionEdited);
		
		emBean.fireEmployee();
	}
}
