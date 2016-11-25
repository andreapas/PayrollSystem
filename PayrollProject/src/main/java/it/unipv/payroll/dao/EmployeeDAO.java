package it.unipv.payroll.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unipv.payroll.model.Employee;

@Stateless
public class EmployeeDAO {

	@PersistenceContext
	EntityManager em;
	
	public List<Employee> findAll() {
		
		List<Employee> employeeList =
				em.createQuery("select e from Employee e", Employee.class)
				.getResultList();
		
		return employeeList;
	}

	public void add(Employee anEmployee) {
		em.persist(anEmployee);
	}

	public void remove(Employee anEmployee) {
		em.remove(em.find(Employee.class, anEmployee.getCode()));
	}

	public void update(Employee anEmployee) {
		em.merge(anEmployee);
		
	}

}
