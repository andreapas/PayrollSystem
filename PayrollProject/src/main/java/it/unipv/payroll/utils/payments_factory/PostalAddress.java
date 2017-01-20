package it.unipv.payroll.utils.payments_factory;

import it.unipv.payroll.model.IEmployee;

public class PostalAddress implements IPaymentsMethod {

		public static String NAME= "Postal address";

	@Override
	public String getPaymentDetails(IEmployee employee) {
		return employee.getAddress();
	}

}
