package it.unipv.payroll.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import it.unipv.payroll.dao.TransactionsInfoDAO;
import it.unipv.payroll.model.TransactionsInfo;

@Stateless
public class TransactionsInfoController {
	
	@Inject TransactionsInfoDAO tiDAO;
	Logger logger = Logger.getLogger(TransactionsInfoController.class);
	
	@PostConstruct
	public void init() {
		logger.info("TransactionsInfoController ready to receive new commands!");
	}

	public List<TransactionsInfo> addTransactionsInfo(TransactionsInfo transactionsInfo) throws Exception {

		if(transactionsInfo.getCode().isEmpty()||transactionsInfo.getCode().equals(null)){
			
			throw new Exception("The code cannot be null");
		}
		
		tiDAO.add(transactionsInfo);
		List<TransactionsInfo> allTransactionsInfo = tiDAO.findAll();
		
		return allTransactionsInfo;
	}

	public List<TransactionsInfo> getAllTransactionsInfo() {
		return tiDAO.findAll();
	}

}
