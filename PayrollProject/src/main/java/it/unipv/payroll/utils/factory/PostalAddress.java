package it.unipv.payroll.utils.factory;

import it.unipv.payroll.model.Employee;

public class PostalAddress implements IPaymentsMethod {

		public static String NAME= "Postal address";

	@Override
	public String getPaymentDetails(Employee employee) {
		return employee.getAddress();
	}

}
