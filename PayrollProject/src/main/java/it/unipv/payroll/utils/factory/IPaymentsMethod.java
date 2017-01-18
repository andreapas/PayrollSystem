package it.unipv.payroll.utils.factory;

import it.unipv.payroll.model.Employee;

public interface IPaymentsMethod {

	public String getPaymentDetails(Employee employee);
	
	
}
