package it.unipv.payroll.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import it.unipv.payroll.model.FullTimeEmployee;
import it.unipv.payroll.model.PartTimeEmployee;
import it.unipv.payroll.utils.payments_factory.IPaymentsFactory;
import it.unipv.payroll.utils.payments_factory.PaymentFactory;

@Stateless
public class PartTimeController extends GenericController<PartTimeEmployee> {

	@Inject
	IPaymentsFactory factory;
	
	public List<PartTimeEmployee> findAll() {
		List<PartTimeEmployee> list = dao.findAll();
		return list;
	}

	@Override
	public boolean isAlreadyInDatabase(PartTimeEmployee element) {
		PartTimeEmployee employee = dao.find(element.getCode());
		if (employee != null) {
			return true;
		}
		return false;
	}

	@Override
	public PartTimeEmployee find(Object id) throws Exception {
		String pk = (String) id;
		if (pk == null || pk.isEmpty())
			throw new Exception("Cannot find null or empty id. " + ERROR);
		return dao.find(id);
	}

	@Override
	public boolean isElementOk(PartTimeEmployee element) {
		if (element.getCode().isEmpty() || element.getCode() == null)
			return false;
		if (element.getAddress().isEmpty() || element.getAddress() == null)
			return false;
		if (element.getEmail().isEmpty() || element.getEmail() == null)
			return false;
		if (element.getName().isEmpty() || element.getName() == null)
			return false;
		if (element.getHourlyRate() == 0)
			return false;
		if (element.getSurname().isEmpty() || element.getSurname() == null)
			return false;
		if (element.getUnion() == null)
			return false;
		if (element.getPayment_method().isEmpty() || element.getPayment_method() == null) {
			return false;
		} else {
			String paymentDetails = factory.getPayment(element.getPayment_method()).getPaymentDetails(element);
			if (paymentDetails == null) {
				return false;
			} else {
				element.setRole("Weekly");
				element.setPayment_method_details(paymentDetails);
			}

		}
		return true;
	}
}
