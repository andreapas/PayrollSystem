package it.unipv.payroll.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import it.unipv.payroll.dao.GenericDAO;
import it.unipv.payroll.dao.PayrollDAO;
import it.unipv.payroll.model.Payroll;

@TransactionAttribute(TransactionAttributeType.REQUIRED)
public abstract class GenericController<T extends Serializable> {

	private final Class<T> type;

	private static String SUCCESS = "Operation completed successfully.";
	private static String ERROR = "Something went wrong. No changes has been made.";

	@Inject
	GenericDAO<T> dao;
	Logger logger = Logger.getLogger(UnionsController.class);

	@PostConstruct
	public void init() {
		logger.info("Controller ready to receive new commands!");
	}

	public GenericController(Class<T> type) {
		this.type = type;
	}

	public String add(T element) {
		dao.add(element);
		return SUCCESS;
	}

	public String remove(Object id) {
		dao.remove(id);
		return SUCCESS;
	}

}
