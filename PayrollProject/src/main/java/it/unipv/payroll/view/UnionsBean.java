package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.UnionsController;
import it.unipv.payroll.model.Union;

@Named
@SessionScoped
public class UnionsBean implements Serializable {
	
	@Inject UnionsController unionsController;

	private Union union;
	
	private List<Union> unions;

	public Union getUnion() {
		return union;
	}

	public void setUnion(Union union) {
		this.union = union;
	}

	public String addUnion() {
			String answer=unionsController.add(union);
			return answer;
	}
	
	public String removeUnion(Union union) {
		
		String answer=unionsController.remove(union);
		return answer;
	}
	
}
