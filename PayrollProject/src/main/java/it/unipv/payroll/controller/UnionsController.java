package it.unipv.payroll.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import it.unipv.payroll.dao.PayrollDAO;
import it.unipv.payroll.dao.UnionsDAO;
import it.unipv.payroll.model.Payroll;
import it.unipv.payroll.model.Union;

@Stateless
public class UnionsController extends GenericController<Union>{


//	@Inject	UnionsDAO unionsDao;
//	Logger logger = Logger.getLogger(UnionsController.class);
//	
//	@PostConstruct
//	public void init() {
//		logger.info("UnionsController ready to receive new commands!");
//	}
	
	public List<Union> getAllUnions(){
		return dao.findAll();
	}

	
//	public List<Union> addUnion(Union union) throws Exception{
//		unionsDao.add(union);
//		
//		List<Union> unions = unionsDao.findAll();
//				
//		return unions; //Ma WTF!
//	}

//	public List<Union> remove(Union union) {
//		
//		unionsDao.remove(union.getUnionName());
//		
//		List<Union> unions = unionsDao.findAll();
//		
//		return unions; //Ma WTF!
//	}
//	
}
