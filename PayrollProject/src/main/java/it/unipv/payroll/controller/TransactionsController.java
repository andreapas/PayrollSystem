package it.unipv.payroll.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.mail.javamail.JavaMailSender;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FullTimeEmployee;
import it.unipv.payroll.model.PartTimeEmployee;
import it.unipv.payroll.model.Transactions;
import it.unipv.payroll.utils.PdfGenerator;

@Stateless
public class TransactionsController extends GenericController<Transactions> {

	public void addSale(Transactions element) throws Exception{
		element.setAmount((float)(element.getAmount()*(((FullTimeEmployee)element.getEmployee()).getCommissionRate()/100)));
		super.add(element);
	}

	public void addHours(Transactions element, int numberOfHours) throws Exception {
		element.setDate(new Date());
		float rate=((PartTimeEmployee)element.getEmployee()).getHourlyRate();
		if (numberOfHours > 8) {
			element.setAmount((float) (8 * rate + 1.5 * (numberOfHours - 8) * rate));
		} else {
			element.setAmount((float) (numberOfHours * rate));
		}
		element.setInfo("Worked hours");
		super.add(element);
		
	}

	public void addCharge(Transactions element) throws Exception{
		element.setDate(new Date());
		element.setAmount(-element.getAmount());
		super.add(element);
	}
	
	public List<Transactions> findAll() {
		return dao.findAll();
	}

	@Override
	public boolean isAlreadyInDatabase(Transactions element) {
		Transactions tinfo = dao.find(element.getId());
		if (tinfo != null) {
			return true;
		}
		return false;
	}

	@Override
	public Transactions find(Object id) throws Exception {
		return dao.find(id);
	}

	public HashMap<Employee, Float> pay(String role) throws Exception {
		HashMap<Employee, Float> earnings = new HashMap<Employee, Float>();
		PdfGenerator generator = new PdfGenerator();
		for (Transactions ut : findAll()) {
			if (!ut.isExecuted()) {

				if (ut.getEmployee().getRole().equals(role)) {
					if (earnings.containsKey(ut.getEmployee())) {
						earnings.put(ut.getEmployee(), earnings.get(ut.getEmployee().getCode()) + ut.getAmount());
					} else {
						earnings.put(ut.getEmployee(), ut.getAmount());
					}
					ut.setExecuted(true);
					update(ut);
				}
			}
		}
		try {
			Document document = generator.generatePdf(earnings);
		} catch (DocumentException e) {
			logger.error("Error while generating the pdf.");
		}
		return earnings;
	}

	private void sendMail(Document document) {
		JavaMailSender mailSender;
		// mailSender.createMimeMessage();

	}

	@Override
	public boolean isElementOk(Transactions element) {
		if (element.getEmployee() == null)
			return false;
		if (element.getDate() == null)
			return false;
		if (element.getInfo().isEmpty() || element.getInfo() == null)
			return false;
		return true;

	}
}
