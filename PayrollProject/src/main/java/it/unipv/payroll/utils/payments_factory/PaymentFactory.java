package it.unipv.payroll.utils.payments_factory;

import javax.ejb.Stateless;

@Stateless
public class PaymentFactory implements IPaymentsFactory {

	
	
	/* (non-Javadoc)
	 * @see it.unipv.payroll.utils.factory.IPaymentsFactory#getPayment(java.lang.String)
	 */
	@Override
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
}
