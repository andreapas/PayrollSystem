package it.unipv.payroll.controller;

import java.util.List;

import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FullTimeEmployee;

public class FullTimeController extends GenericController<FullTimeEmployee>{

	
	public List<FullTimeEmployee> findAll() {
		List<FullTimeEmployee> list= dao.findAll();
		return list;
	}

	
	@Override
	public boolean isAlreadyInDatabase(FullTimeEmployee element) {
		FullTimeEmployee employee= dao.find(element.getCode());
		if(employee!=null){
			return true;
		}
		return false;
	}
	
}
