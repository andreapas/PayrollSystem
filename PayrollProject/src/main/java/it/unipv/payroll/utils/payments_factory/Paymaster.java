package it.unipv.payroll.utils.payments_factory;

import it.unipv.payroll.model.IEmployee;

public class Paymaster implements IPaymentsMethod {

	public static String NAME="Paymaster";

	@Override
	public String getPaymentDetails(IEmployee employee) {
		// TODO Auto-generated method stub
		return "";
	}

}
