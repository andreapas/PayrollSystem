package it.unipv.payroll.dao;

import javax.ejb.Stateless;

import it.unipv.payroll.model.Payroll;
import it.unipv.payroll.model.PayrollUsername;

@Stateless
public class PayrollUsernameDAO extends GenericDAO<PayrollUsername>{

	/* (non-Javadoc)
	 * @see it.unipv.payroll.dao.GenericPayroll#findAll()
	 */
	public PayrollUsernameDAO() {
		super(PayrollUsername.class);
	}
}
