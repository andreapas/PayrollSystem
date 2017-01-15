package it.unipv.payroll.utils;

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
import it.unipv.payroll.view.TransactionsBean;

@Stateless
public class AutoPayday {

	@Inject
	TransactionsBean tBean;
	@Inject
	TransactionsController tController;
	@Inject
	EmployeeController emController;

	@Resource
	TimerService timerService;

	@Schedule(dayOfWeek = "Fri")
	// @Schedule(hour = "18", minute = "20", second = "30")
	public HashMap<String, Float> weeklyPay() {
		List<Employee> employeeList= emController.findAll();
		for (Employee employee : employeeList) {
			System.out.println(employee.getCode());
			Transactions trans=new Transactions();
			tBean.setTransaction(trans);
			tBean.getTransaction().setAmount(employee.getUnion().getWeeklyRate());
			tBean.getTransaction().setInfo("Weekly charge for being an associate to the union: "+employee.getUnion().getUnionName());
			tBean.getEmployeeList().add(employee);
			tBean.addServiceCharge();
			tBean.getEmployeeList().clear();
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
		if (employeesEarnings.size() == 0) {
			System.out.println("noone has been paid :(");
		}
		return employeesEarnings;
	}

	@Schedule(dayOfMonth = "Last")
	// @Schedule(hour = "18", minute = "20", second = "25")
	public HashMap<String, Float> monthlyPay() {
		List<Employee> emList = emController.findAll();
		HashMap<String, Float> employeesEarnings = new HashMap<String, Float>();
		for (Employee employee : emList) {
			if (employee.getRole().equals("Monthly")) {
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
