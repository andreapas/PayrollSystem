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


	private static String SUCCESS = "Operation completed successfully.";
	private static String ERROR = "Something went wrong. No changes has been made.";

	@Inject GenericDAO<T> dao;
	Logger logger = Logger.getLogger(UnionsController.class);

	@PostConstruct
	public void init() {
		logger.info("Controller ready to receive new commands!");
	}
	
	public String add(T element) {
		dao.add(element);
		return SUCCESS;
	}

	public String remove(Object id) {
		dao.remove(id);
		return SUCCESS;
	}

	public String update(T element) {
		dao.update(element);
		return SUCCESS;
	}
}
