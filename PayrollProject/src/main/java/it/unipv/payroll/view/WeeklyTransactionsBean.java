package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import it.unipv.payroll.controller.ChargesController;
import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.controller.PartTimeController;
import it.unipv.payroll.controller.TimeCardController;
import it.unipv.payroll.model.Charges;
import it.unipv.payroll.model.IEmployee;
import it.unipv.payroll.utils.Mail;
import it.unipv.payroll.utils.ReportCreator;

@RequestScoped
public class WeeklyTransactionsBean implements Serializable {

	@Inject
	TimeCardController tcController;
	@Inject
	ChargesController cController;
	@Inject
	PartTimeController ptController;
	@Inject
	EmployeeController emController;
	@Inject
	Mail mailer;

	private String managerEmail;
	private ReportCreator fileCreator = new ReportCreator();
	private List<IEmployee> emList = new ArrayList<IEmployee>();
	private List<IEmployee> ptList = new ArrayList<IEmployee>();

	public HashMap<String, Float> pay() {
		ptList.addAll(ptController.findAll());
		List<String> codeEmList = new ArrayList<String>();
		for (IEmployee employee : ptList) {
			codeEmList.add(employee.getCode());
		}
		HashMap<String, Float> total = new HashMap<String, Float>();
		try {
			HashMap<String, Float> dues = cController.chargeFee(codeEmList);
			HashMap<String, Float> earnings = tcController.payTimeCards(codeEmList);

			for (String code : codeEmList) {
				total.put(code, dues.get(code) + earnings.get(code));
			}
			emList.clear();
			emList.addAll(ptList);
			managerEmail = emController.getManager().getEmail();
			sendMail(emList, earnings, "Part-time", managerEmail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;

	}

	public void addWeeklyUnionFee() {
		emList.addAll(emController.findAll());

		for (IEmployee employee : emList) {
			Charges trans = new Charges();
			trans.setAmount(employee.getUnion().getWeeklyRate());
			trans.setEmployee(employee);
			trans.setInfo("Weekly charge for being an associate to the union: " + employee.getUnion().getUnionName());
			try {
				cController.addCharge(trans);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void sendMail(List<IEmployee> employeeList, HashMap<String, Float> earnings, String type,
			String recipientEmail) {
		String file = fileCreator.generateFile(employeeList, earnings, type);
		mailer.send(recipientEmail, "Reminder of payed employees " + type,
				"Attached you can find the report of the payment executed or waiting to be delivered from the Paymaster.\n\n\nThis mail has been auto-generated. do not reply.",
				file);
	}
}
