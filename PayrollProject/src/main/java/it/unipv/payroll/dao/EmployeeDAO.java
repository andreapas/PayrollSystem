package it.unipv.payroll.dao;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Employee;

@Stateless
public class EmployeeDAO extends GenericDAO<Employee>{

	public EmployeeDAO() {
		super(Employee.class);
	}

}
