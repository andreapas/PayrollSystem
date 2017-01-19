package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import it.unipv.payroll.controller.ChargesController;
import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.controller.FullTimeController;
import it.unipv.payroll.controller.SalaryController;
import it.unipv.payroll.controller.SalesController;
import it.unipv.payroll.model.FullTimeEmployee;
import it.unipv.payroll.model.IEmployee;
import it.unipv.payroll.model.Salary;
import it.unipv.payroll.utils.Mail;
import it.unipv.payroll.utils.ReportCreator;

@RequestScoped
public class MonthlyTransactionsBean implements Serializable{
	@Inject
	SalesController sController;
	@Inject
	SalaryController saController;
	@Inject
	ChargesController cController;
	@Inject
	FullTimeController ftController;
	@Inject
	EmployeeController emController;
	@Inject
	Mail mailer;

	private String managerEmail;
	private ReportCreator fileCreator = new ReportCreator();
	private List<FullTimeEmployee> ftList;
	private List<IEmployee> emList = new ArrayList<IEmployee>();

	public HashMap<String, Float> pay() {
		ftList = ftController.findAll();
		List<String> codeEmList = new ArrayList<String>();
		for (IEmployee employee : ftList) {
			codeEmList.add(employee.getCode());
		}
		HashMap<String, Float> total = new HashMap<String, Float>();
		try {
			HashMap<String, Float> dues = cController.chargeFee(codeEmList);
			HashMap<String, Float> salesEarnings = sController.paySales(codeEmList);
			HashMap<String, Float> salaryEarnings = saController.paySalary(codeEmList);
			float due=0;
			float sale=0;
			float salary=0;
			for (String code : codeEmList) {
				due=0;
				sale=0;
				salary=0;
				if(dues.containsKey(code))
					due=dues.get(code);
				if(salesEarnings.containsKey(code))
					sale=salesEarnings.get(code);
				if(salaryEarnings.containsKey(code))
					salary=salaryEarnings.get(code);
				total.put(code, due+sale+salary);
			}
			managerEmail = emController.getManager().getEmail();
			emList.addAll(ftList);
			sendMail(emList, total, "Full-time", managerEmail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;

	}

	public void addSalaries() {
		ftList = ftController.findAll();
		for (FullTimeEmployee employee : ftList) {
			Salary trans = new Salary();
			trans.setDate(new Date());
			trans.setAmount(employee.getSalary());
			trans.setEmployee(employee);
			trans.setInfo("Monthly salary");
			try {
				saController.add(trans);
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
