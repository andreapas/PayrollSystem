package it.unipv.payroll.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TimerService;
import javax.inject.Inject;

import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.controller.FullTimeController;
import it.unipv.payroll.controller.TransactionsController;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FullTimeEmployee;
import it.unipv.payroll.model.Transactions;

@Stateless
public class AutoPayday {

	@Inject
	TransactionsController tController;
	@Inject
	EmployeeController emController;
	@Inject
	FullTimeController ftController;
	@Inject
	Mail mailer;

	private ReportCreator fileCreator = new ReportCreator();

	@Resource
	TimerService timerService;

	@Schedule(dayOfWeek = "Fri", hour = "8", minute = "30")
	// @Schedule(hour = "16", minute = "33", second = "30")
	public HashMap<String, Float> weeklyPay() {
		List<Employee> employeeList = emController.findAll();
		List<String> codeEmList = new ArrayList<String>();
		String managerEmail = "";
		try {
			for (Iterator iterator = employeeList.iterator(); iterator.hasNext();) {
				Employee employee = (Employee) iterator.next();				
				Transactions trans = new Transactions();
				trans.setAmount(employee.getUnion().getWeeklyRate());
				trans.setEmployee(employee);
				trans.setInfo(
						"Weekly charge for being an associate to the union: " + employee.getUnion().getUnionName());
				tController.addCharge(trans);
				if (employee.getRole().equals("Manager"))
					managerEmail = employee.getEmail();
				if (employee.getRole().equals("Weekly")) {
					codeEmList.add(employee.getCode());
				}else{
					iterator.remove();
				}
				
			}
			HashMap<String, Float> earnings = tController.pay(codeEmList);
			sendMail(employeeList, earnings, "Part-time", managerEmail);
			return earnings;

		} catch (Exception e) {
			System.out.println("ERROR: cacchio" + e.getMessage());
			return null;
		}
	}

	@Schedule(dayOfMonth = "Last", hour = "8", minute = "30")
	// @Schedule(hour = "16", minute = "33", second = "35")
	public HashMap<String, Float> monthlyPay() {
		List<FullTimeEmployee> ftList = ftController.findAll();
		List<String> codeEmList = new ArrayList<String>();
		String managerEmail = "";
		try {

			for (FullTimeEmployee employee : ftList) {
				codeEmList.add(employee.getCode());
				Transactions trans = new Transactions();
				trans.setAmount(employee.getSalary());
				trans.setDate(new Date());
				trans.setEmployee(employee);
				trans.setInfo("Monthly Salary");
				tController.add(trans);
				if (employee.getRole().equals("Manager"))
					managerEmail = employee.getEmail();
			}
			List<Employee> emList = new ArrayList<Employee>();
			emList.addAll(ftList);
			HashMap<String, Float> earnings = tController.pay(codeEmList);
			sendMail(emList, earnings, "Full-time", managerEmail);
			return earnings;
		} catch (Exception e) {
			System.out.println("ERROR: shit" + e.getMessage());
			return null;
		}
	}

	private void sendMail(List<Employee> employeeList, HashMap<String, Float> earnings, String type,
			String recipientEmail) {
		String file = fileCreator.generateFile(employeeList, earnings, type);
		mailer.send(recipientEmail, "Reminder of payed employees " + type,
				"Attached you can find the report of the payment executed or waiting to be delivered from the Paymaster.\n\n\nThis mail has been auto-generated. do not reply.",
				file);
//		System.out.println("printing: "+type);
//		for (Employee employee : employeeList) {
//			System.out.println(employee.getRole());
//		}
	}

}
