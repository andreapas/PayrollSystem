package it.unipv.payroll.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TimerService;
import javax.inject.Inject;

import it.unipv.payroll.controller.EmployeeController;
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

	@Resource
	TimerService timerService;

	@Schedule(dayOfWeek = "Fri", hour="8", minute="30")
//	@Schedule(hour = "16", minute = "33", second = "30")
	public HashMap<String, Float> weeklyPay() {
		List<Employee> employeeList= emController.findAll();
		for (Employee employee : employeeList) {
			System.out.println(employee.getCode());
			Transactions trans=new Transactions();
			trans.setAmount(-employee.getUnion().getWeeklyRate());
			trans.setDate(new Date());
			trans.setEmployee(employee);
			trans.setInfo("Weekly charge for being an associate to the union: "+employee.getUnion().getUnionName());
			tController.add(trans);
		}
		HashMap<String, Float> employeesEarnings = new HashMap<String, Float>();
		List<Transactions> allTransactions = tController.findAll();
		for (Transactions ut : allTransactions) {
			if (!ut.isExecuted()) {

				if (ut.getEmployee().getRole().equals("Weekly")) {
					if (employeesEarnings.containsKey(ut.getEmployee().getCode())) {
						employeesEarnings.put(ut.getEmployee().getCode(),
								employeesEarnings.get(ut.getEmployee().getCode()) + ut.getAmount());
					} else {
						employeesEarnings.put(ut.getEmployee().getCode(), ut.getAmount());
					}
					ut.setExecuted(true);
					tController.update(ut);
				}
			}
		}
		Set<String> keys = employeesEarnings.keySet();
		for (String key : keys) {
			System.out.println(key + " has been payed " + employeesEarnings.get(key));
		}
		return employeesEarnings;
	}

	@Schedule(dayOfMonth = "Last", hour="8", minute="30")
//	@Schedule(hour = "16", minute = "33", second = "35")
	public HashMap<String, Float> monthlyPay() {
		List<Employee> emList = emController.findAll();
		HashMap<String, Float> employeesEarnings = new HashMap<String, Float>();
		for (Employee employee : emList) {
			if (!employee.getRole().equals("Weekly")) {
				FullTimeEmployee full = (FullTimeEmployee) employee;
				employeesEarnings.put(full.getCode(), full.getSalary());
			}
		}
		List<Transactions> allTransactions = tController.findAll();
		for (Transactions ut : allTransactions) {
			if (!ut.isExecuted()) {

				if (ut.getEmployee().getRole().equals("Monthly")) {
					employeesEarnings.put(ut.getEmployee().getCode(),
							employeesEarnings.get(ut.getEmployee().getCode()) + ut.getAmount());
					ut.setExecuted(true);
					tController.update(ut);
				}
			}
		}
		Set<String> keys = employeesEarnings.keySet();
		for (String key : keys) {
			System.out.println(key + " has been payed " + employeesEarnings.get(key));
		}
		return employeesEarnings;
	}

	// private void cancelTimers() {
	// for (Timer timer : timerService.getTimers()) {
	// timer.cancel();
	// }
	// }
}
