package it.unipv.payroll.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
	
	private ReportCreator fileCreator=new ReportCreator();
	
	@Resource
	TimerService timerService;

	@Schedule(dayOfWeek = "Fri", hour = "8", minute = "30")
	// @Schedule(hour = "16", minute = "33", second = "30")
	public HashMap<String, Float> weeklyPay() {
		List<Employee> employeeList = emController.findAll();
		try {
			for (Employee employee : employeeList) {
				Transactions trans = new Transactions();
				trans.setAmount(employee.getUnion().getWeeklyRate());
				trans.setEmployee(employee);
				trans.setInfo(
						"Weekly charge for being an associate to the union: " + employee.getUnion().getUnionName());
				// TODO: MODIFICA STAMMERDA

				tController.addCharge(trans);

				// tController.add(trans);
			}
			HashMap<String, Float>earnings= tController.pay("Weekly");
			sendMail(employeeList, earnings, "Part-time");
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
		try {

			for (FullTimeEmployee employee : ftList) {
				Transactions trans = new Transactions();
				trans.setAmount(employee.getSalary());
				trans.setDate(new Date());
				trans.setEmployee(employee);
				trans.setInfo("Monthly Salary");
				tController.add(trans);
			}
			List<Employee> emList=new ArrayList<Employee>();
			emList.addAll(ftList);
			HashMap<String, Float>earnings= tController.pay("Monthly");
			sendMail(emList, earnings, "Full-time");
			return earnings;
		} catch (Exception e) {
			System.out.println("ERROR: shit" + e.getMessage());
			return null;
		}
	}

	private void sendMail(List<Employee>employeeList,HashMap<String, Float>earnings, String type ){
		String file=fileCreator.generateFile(employeeList, earnings, type);
		mailer.send("andrea_pasquali@hotmail.it", "Reminder of payed employees "+type, "Attached you can find the report of the payment executed or waiting to be delivered from the Paymaster.\n\n\nThis mail has been auto-generated. do not reply.", file);
	}
	
}
