package it.unipv.payroll.controller;

import java.util.List;

import javax.ejb.Stateless;

import it.unipv.payroll.model.PartTimeEmployee;

@Stateless
public class PartTimeController extends GenericController<PartTimeEmployee> {

	
	public List<PartTimeEmployee> findAll() {
		List<PartTimeEmployee> list = dao.findAll();
		return list;
	}

	
	@Override
	public boolean isAlreadyInDatabase(PartTimeEmployee element) {
		PartTimeEmployee employee= dao.find(element.getCode());
		if(employee!=null){
			return true;
		}
		return false;
	}
}
