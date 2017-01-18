package it.unipv.payroll.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import it.unipv.payroll.view.FullTimeBean;

@Stateless
public class AutoPayday {

	@Inject
	TransactionsController tController;
	@Inject
	EmployeeController emController;
	@Inject
	FullTimeBean ftBean;

	@Resource
	TimerService timerService;

	@Schedule(dayOfWeek = "Fri", hour = "8", minute = "30")
	// @Schedule(hour = "16", minute = "33", second = "30")
	public HashMap<Employee, Float> weeklyPay() {
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
			return tController.pay("Weekly");
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return null;
		}
	}

	@Schedule(dayOfMonth = "Last", hour = "8", minute = "30")
	// @Schedule(hour = "16", minute = "33", second = "35")
	public HashMap<Employee, Float> monthlyPay() {
		List<FullTimeEmployee> ftList = ftBean.getFullTimersList();
		try {

			for (FullTimeEmployee employee : ftList) {
				Transactions trans = new Transactions();
				trans.setAmount(employee.getSalary());
				trans.setDate(new Date());
				trans.setEmployee(employee);
				trans.setInfo("Monthly Salary");
				tController.add(trans);
			}
			List<Transactions> allTransactions = tController.findAll();
			return tController.pay("Monthly");
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return null;
		}
	}

	// private void cancelTimers() {
	// for (Timer timer : timerService.getTimers()) {
	// timer.cancel();
	// }
	// }
}
