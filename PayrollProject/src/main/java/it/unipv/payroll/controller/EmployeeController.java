package it.unipv.payroll.controller;

import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Employee;

@Stateless
public class EmployeeController extends GenericController<Employee> {

	public Employee find(String code) {
		return dao.find(code);
	}

	public List<Employee> findAll() {
		List<Employee> list = dao.findAll();
		for (Iterator<Employee> iterator = list.iterator(); iterator.hasNext();) {
			Employee employee = (Employee) iterator.next();
			if (employee.getRole().equals("Manager")) {
				iterator.remove();
			}
		}
		return list;
	}

	@Override
	public boolean isAlreadyInDatabase(Employee element) {
		Employee employee= dao.find(element.getCode());
		if(employee!=null){
			return true;
		}
		return false;
	}
}
