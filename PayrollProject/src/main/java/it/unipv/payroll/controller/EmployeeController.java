package it.unipv.payroll.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import it.unipv.payroll.dao.EmployeeDAO;
import it.unipv.payroll.model.Employee;

@Stateless
public class EmployeeController {

	
	@Inject	EmployeeDAO emDAO;
	Logger logger = Logger.getLogger(PayrollController.class);
	
	@PostConstruct
	public void init() {
		logger.info("EmployeeController ready to receive new commands!");
	}

	public List<Employee> hireEmployee(Employee anEmployee) throws Exception{

		if (anEmployee.getCode().isEmpty()||anEmployee.getCode().equals(null)) {
			throw new Exception("Code cannot be null");
		}
		if (anEmployee.getName().isEmpty()||anEmployee.getName().equals(null)) {
			throw new Exception("Name cannot be null");			
		}
		if (anEmployee.getSurname().isEmpty()||anEmployee.getSurname().equals(null)) {
			throw new Exception("Surname cannot be null");			
		}
		if (anEmployee.getEmail().isEmpty()||anEmployee.getEmail().equals(null)) {
			throw new Exception("Email cannot be null");			
		}

		emDAO.add(anEmployee);
		
		List<Employee>employeeList=emDAO.findAll();
		
		return employeeList;
		
	}

	public List<Employee> fireEmployee(Employee anEmployee) {
		emDAO.remove(anEmployee.getCode());
		
		List<Employee>employeeList=emDAO.findAll();
		
		return employeeList;
	}

	public List<Employee> editEmail(Employee anEmployee) {
		emDAO.update(anEmployee);
		
		List<Employee>employeeList=emDAO.findAll();
		
		return employeeList;
	}
	public List<Employee> editUnion(Employee anEmployee) {
		emDAO.update(anEmployee);
		
		List<Employee>employeeList=emDAO.findAll();
		
		return employeeList;
	}
	
	

}
