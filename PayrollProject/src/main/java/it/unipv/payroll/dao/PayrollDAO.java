package it.unipv.payroll.dao;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Payroll;

@Stateless
public class PayrollDAO extends GenericDAO<Payroll>{

	/* (non-Javadoc)
	 * @see it.unipv.payroll.dao.GenericPayroll#findAll()
	 */
	public PayrollDAO() {
		super(Payroll.class);
	}
}
