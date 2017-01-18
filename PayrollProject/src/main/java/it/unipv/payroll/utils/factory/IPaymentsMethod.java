package it.unipv.payroll.utils.factory;

import it.unipv.payroll.model.IEmployee;

public interface IPaymentsMethod {

	public String getPaymentDetails(IEmployee employee);
	
	
}
