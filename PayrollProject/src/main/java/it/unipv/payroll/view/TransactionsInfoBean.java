package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.TransactionsInfoController;
import it.unipv.payroll.model.TransactionsInfo;

@Named
@SessionScoped
public class TransactionsInfoBean implements Serializable {

	@Inject
	TransactionsInfoController tiController;

	private TransactionsInfo transactionsInfo;
	private List<TransactionsInfo> allTransactionsInfo;


	public TransactionsInfo getTransactionsInfo() {
		return transactionsInfo;
	}

	public void setTransactionsInfo(TransactionsInfo transactionsInfo) {
		this.transactionsInfo = transactionsInfo;
	}

	public List<TransactionsInfo> getAllTransactionsInfo() {
		allTransactionsInfo.addAll(tiController.findAll());
		return allTransactionsInfo;
	}
	
	public String addTransactionsInfo(){
		
		String answer = tiController.add(transactionsInfo);
	
		return answer;
	}
	
	public String removeTransactionInfo(TransactionsInfo transactionsInfo){
		String answer= tiController.remove(transactionsInfo.getId());
		return answer;
	}
	

}
