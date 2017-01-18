package it.unipv.payroll.utils.factory;

import java.util.ArrayList;
import java.util.List;

public class PaymentFactory {

	private List<String> listPaymentsAvailable;
	public PaymentFactory() {
		listPaymentsAvailable=new ArrayList<String>();
		listPaymentsAvailable.add(Paymaster.NAME);
		listPaymentsAvailable.add(PostalAddress.NAME);
		listPaymentsAvailable.add(BankAccount.NAME);
		
		
	}
	
	public IPaymentsMethod getPayment(String name){
		if (name==null) {
			return null;
		}
		if (name.equals(Paymaster.NAME)) {
			return new Paymaster();
		}else if (name.equals(PostalAddress.NAME)) {
			return new PostalAddress();
		}else if (name.equals(BankAccount.NAME)) {
			return new BankAccount();
		}
		return null;
	}
	public List<String> getListPaymentsAvailable() {
		return listPaymentsAvailable;
	}
}
