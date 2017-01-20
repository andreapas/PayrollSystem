package it.unipv.payroll.utils.payments_factory;

import javax.ejb.Stateless;

@Stateless
public interface IPaymentsFactory {

	IPaymentsMethod getPayment(String name);

}