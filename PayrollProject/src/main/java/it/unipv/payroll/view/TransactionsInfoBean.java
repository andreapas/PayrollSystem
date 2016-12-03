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

	@PostConstruct
	public void init() {
		transactionsInfo = new TransactionsInfo();
		allTransactionsInfo = tiController.getAllTransactionsInfo();
	}

	public TransactionsInfo getTransactionsInfo() {
		return transactionsInfo;
	}

	public void setTransactionsInfo(TransactionsInfo transactionsInfo) {
		this.transactionsInfo = transactionsInfo;
	}

	public List<TransactionsInfo> getAllTransactionsInfo() {
		return allTransactionsInfo;
	}

	public void setAllTransactionsInfo(List<TransactionsInfo> allTransactionsInfo) {

		this.allTransactionsInfo = allTransactionsInfo;

	}
	
	public void addTransactionsInfo(){
		
		try{
			
			allTransactionsInfo = tiController.addTransactionsInfo(transactionsInfo);
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
		
	}

}
