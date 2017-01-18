package it.unipv.payroll.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import it.unipv.payroll.dao.GenericDAO;

@TransactionAttribute(TransactionAttributeType.REQUIRED)
public abstract class GenericController<T extends Serializable> {

	public static String ERROR = "No changes has been made.";

	@Inject
	GenericDAO<T> dao;
	Logger logger = Logger.getLogger(UnionsController.class);

	@PostConstruct
	public void init() {
	}

	public void add(T element) throws Exception {
		if (isAlreadyInDatabase(element)||!isElementOk(element))
			throw new Exception("Element already present. " + ERROR);
		dao.add(element);
	}

	public void remove(String id) throws Exception {
		if (id == null || id.isEmpty())
			throw new Exception("Cannot remove null or empty id. " + ERROR);
		if(find(id)==null)
			throw new Exception("Entity not found in the database. " + ERROR);
		dao.remove(id);

	}


	public void update(T element) throws Exception {
		if (!isAlreadyInDatabase(element))
			throw new Exception("Element is not present. " + ERROR);
		dao.update(element);

	}

	public abstract boolean isAlreadyInDatabase(T element);

	public abstract boolean isElementOk(T element);
	
	public abstract T find(Object id) throws Exception;

}
