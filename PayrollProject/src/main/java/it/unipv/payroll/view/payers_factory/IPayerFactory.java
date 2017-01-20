package it.unipv.payroll.view.payers_factory;

import javax.ejb.Stateless;

@Stateless
public interface IPayerFactory {

	IPayer getPayerFromType(String type);

}