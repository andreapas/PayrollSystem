package it.unipv.payroll.controller;

import java.util.List;

import javax.ejb.Stateless;

import it.unipv.payroll.model.IUnion;
import it.unipv.payroll.model.Union;

@Stateless
public class UnionsController extends GenericController<Union> {

	public List<Union> getUnionsList() {
		return dao.findAll();
	}

	@Override
	public Union find(Object id) throws Exception {
		String pk=(String)id;
		if (pk == null || pk.isEmpty())
			throw new Exception("Cannot find null or empty id. " + ERROR);
		return dao.find(id);
	}
	
	@Override
	public boolean isAlreadyInDatabase(Union element) {
		IUnion union = dao.find(element.getUnionName());
		if (union != null) {
			return true;
		}
		return false;
	}

	@Override
	public void remove(Object id) throws Exception {
		if (find(id).getAssociates().size()!=0)
			throw new Exception("Union with associates cannot be removed. No changes has been made.");

		super.remove(id);
	}
	
	@Override
	public boolean isElementOk(Union element) {
		if (element.getUnionName().isEmpty() || element.getUnionName() == null)
			return false;
		return true;
		
	}
	
	
}
