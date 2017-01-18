package it.unipv.payroll.controller;

import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.IEmployee;

@Stateless
public class EmployeeController extends GenericController<Employee> {

	public List<Employee> findAll() {
		List<Employee> list = dao.findAll();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			IEmployee employee = (IEmployee) iterator.next();
			if(employee.getRole().equals("Manager")){
				iterator.remove();
			}
		}
		return list;
	}
	
	@Override
	public boolean isAlreadyInDatabase(Employee element) {
		IEmployee employee= dao.find(element.getCode());
		if(employee!=null){
			return true;
		}
		return false;
	}
	@Override
	public Employee find(Object id) throws Exception {
		String pk=(String)id;
		if (pk == null || pk.isEmpty())
			throw new Exception("Cannot find null or empty id. " + ERROR);
		return dao.find(id);
	}
	@Override
	public boolean isElementOk(Employee element) {
		return false;
	}
}
