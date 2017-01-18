package it.unipv.payroll.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.Transactions;
import it.unipv.payroll.utils.PdfGenerator;

@Stateless
public class TransactionsController extends GenericController<Transactions> {

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
		HashMap<Employee, Float> earnings= new HashMap<Employee, Float>();
		PdfGenerator generator=new PdfGenerator();
		for (Transactions ut : findAll()) {
			if (!ut.isExecuted()) {

				if (ut.getEmployee().getRole().equals(role)) {
					if (earnings.containsKey(ut.getEmployee())) {
						earnings.put(ut.getEmployee(),
								earnings.get(ut.getEmployee().getCode()) + ut.getAmount());
					} else {
						earnings.put(ut.getEmployee(), ut.getAmount());
					}
					ut.setExecuted(true);
					update(ut);
				}
			}
		}
		try {
			Document document=generator.generatePdf(earnings);
		} catch (DocumentException e) {
			logger.error("Error while generating the pdf.");
		}
		return earnings;
	}

	private void sendMail(Document document){
		JavaMailSender mailSender;
//		mailSender.createMimeMessage();
		
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
