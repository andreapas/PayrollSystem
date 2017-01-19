package it.unipv.payroll.utils.factory;

import it.unipv.payroll.model.IEmployee;

public class BankAccount implements IPaymentsMethod {

		public static String NAME="Bank account";

	@Override
	public String getPaymentDetails(IEmployee employee) {
		// TODO Auto-generated method stub
		return employee.getPayment_method_details();
	}

}
