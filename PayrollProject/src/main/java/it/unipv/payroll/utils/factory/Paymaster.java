package it.unipv.payroll.utils.factory;

import it.unipv.payroll.model.Employee;

public class Paymaster implements IPaymentsMethod {

	public static String NAME="Paymaster";

	@Override
	public String getPaymentDetails(Employee employee) {
		// TODO Auto-generated method stub
		return "";
	}

}
