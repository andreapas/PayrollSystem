package it.unipv.payroll.controller;

import java.util.List;

import javax.ejb.Stateless;

import org.jboss.as.cli.handlers.ifelse.ElseHandler;

import it.unipv.payroll.model.FullTimeEmployee;
import it.unipv.payroll.utils.factory.PaymentFactory;

@Stateless
public class FullTimeController extends GenericController<FullTimeEmployee> {

	public List<FullTimeEmployee> findAll() {
		List<FullTimeEmployee> list = dao.findAll();
		return list;
	}

	@Override
	public boolean isAlreadyInDatabase(FullTimeEmployee element) {
		FullTimeEmployee employee = dao.find(element.getCode());
		if (employee != null) {
			return true;
		}
		return false;
	}

	@Override
	public FullTimeEmployee find(Object id) throws Exception {
		String pk=(String)id;
		if (pk == null || pk.isEmpty())
			throw new Exception("Cannot find null or empty id. " + ERROR);
		return dao.find(id);
	}
	
	@Override
	public boolean isElementOk(FullTimeEmployee element) {
		if (element==null)
			return false;
		if (element.getCode().isEmpty() || element.getCode() == null)
			return false;
		if (element.getAddress().isEmpty() || element.getAddress() == null)
			return false;
		if (element.getEmail().isEmpty() || element.getEmail() == null)
			return false;
		if (element.getName().isEmpty() || element.getName() == null)
			return false;
		if (element.getSalary() == 0)
			return false;
		if (element.getSurname().isEmpty() || element.getSurname() == null)
			return false;
		if (element.getUnion() == null)
			return false;
		if (element.getPayment_method().isEmpty() || element.getPayment_method() == null){
			return false;
		}else{
			PaymentFactory factory= new PaymentFactory();
			String paymentDetails=factory.getPayment(element.getPayment_method()).getPaymentDetails(element);
			if (paymentDetails==null) {
				return false;
			}else{
				element.setRole("Monthly");
				element.setPayment_method_details(paymentDetails);
			}
			
		}
		return true;
	}
}
