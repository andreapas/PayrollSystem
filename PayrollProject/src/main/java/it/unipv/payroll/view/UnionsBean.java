package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.UnionsController;
import it.unipv.payroll.dao.UnionsDAO;
import it.unipv.payroll.model.Union;

@Named
@SessionScoped
public class UnionsBean implements Serializable {
	
	@Inject UnionsController unionsController;
	@Inject UnionsDAO unionsDao;

	private List<Union> unions;
	
	@PostConstruct
	public void init() {
		
		unions = unionsDao.getUnions();
	}

	public void add(Union union) {
		
		try {
			unionsController.addUnion(union);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void remove(Union union) {
		
		unionsController.remove(union);
	}
	
}
