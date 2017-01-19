package it.unipv.payroll.controller;

import java.util.List;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.IEmployee;

@Stateless
public class EmployeeController extends GenericController<Employee> {

	public List<Employee> findAll() {
		List<Employee> list = dao.findAll();
		return list;
	}
	
	public Employee getManager(){
		List<Employee> list = dao.findAll();
		for (Employee employee : list) {
			if (employee.getRole().equals("Manager")) {
				return employee;
			}
		}
		return null;
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
	public void remove(Object id) throws Exception {
		if(find(id)==null)
			throw new Exception("Entity not found in the database. " + ERROR);
		IEmployee em=find(id);
		if (em.getRole().equals("Manager")) 
			throw new Exception("You're not allowed to remove the Manager. "+ERROR);
		super.remove(id);
	}
	@Override
	public boolean isElementOk(Employee element) {
		return false;
	}
}
