package it.unipv.payroll.controller;

import java.util.List;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Union;

@Stateless
public class UnionsController extends GenericController<Union> {

	// @Inject UnionsDAO unionsDao;
	// Logger logger = Logger.getLogger(UnionsController.class);
	//
	// @PostConstruct
	// public void init() {
	// logger.info("UnionsController ready to receive new commands!");
	// }

	public Union findUnion(String unionName) {
		return dao.find(unionName);
	}

	public List<Union> getUnionsList() {
		return dao.findAll();
	}

	@Override
	public boolean isAlreadyInDatabase(Union element) {
		Union union = dao.find(element.getUnionName());
		if (union != null) {
			return true;
		}
		return false;
	}

	// public List<Union> addUnion(Union union) throws Exception{
	// unionsDao.add(union);
	//
	// List<Union> unions = unionsDao.findAll();
	//
	// return unions; //Ma WTF!
	// }

	// public List<Union> remove(Union union) {
	//
	// unionsDao.remove(union.getUnionName());
	//
	// List<Union> unions = unionsDao.findAll();
	//
	// return unions; //Ma WTF!
	// }
	//
}
