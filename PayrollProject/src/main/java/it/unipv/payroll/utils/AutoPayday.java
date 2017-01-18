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
import it.unipv.payroll.controller.FullTimeController;
import it.unipv.payroll.controller.TransactionsController;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FullTimeEmployee;
import it.unipv.payroll.model.Transactions;
import it.unipv.payroll.view.FullTimeBean;
import it.unipv.payroll.view.TransactionsBean;

@Stateless
public class AutoPayday {

	@Inject
	TransactionsController tController;
	@Inject
	TransactionsBean tBean;
	@Inject
	EmployeeController emController;
	@Inject
	FullTimeBean ftBean;

	@Resource
	TimerService timerService;

	@Schedule(dayOfWeek = "Fri", hour="8", minute="30")
//	@Schedule(hour = "16", minute = "33", second = "30")
	public HashMap<Employee, Float> weeklyPay() {
		List<Employee> employeeList= emController.findAll();
		for (Employee employee : employeeList) {
			Transactions trans=new Transactions();
			trans.setAmount(-employee.getUnion().getWeeklyRate());
			trans.setDate(new Date());
			trans.setEmployee(employee);
			trans.setInfo("Weekly charge for being an associate to the union: "+employee.getUnion().getUnionName());
			tBean.setTransaction(trans);
			//TODO: MODIFICA STAMMERDA
			tBean.addServiceCharge(employee.getCode());
//			tController.add(trans);
		}
		try {
			return tController.pay("Weekly");
		} catch (Exception e) {
			System.out.println("ERROR: "+e.getMessage());
			return null;
		}
	}

	@Schedule(dayOfMonth = "Last", hour="8", minute="30")
//	@Schedule(hour = "16", minute = "33", second = "35")
	public HashMap<Employee, Float> monthlyPay() {
		List<FullTimeEmployee> ftList = ftBean.getFullTimersList();
		for (FullTimeEmployee employee : ftList) {
			Transactions trans=new Transactions();
			trans.setAmount(employee.getSalary());
			trans.setDate(new Date());
			trans.setEmployee(employee);
			trans.setInfo("Monthly Salary");
			tController.add(trans);
		}
		List<Transactions> allTransactions = tController.findAll();
		try {
			return tController.pay("Monthly");
		} catch (Exception e) {
			System.out.println("ERROR: "+e.getMessage());
			return null;
		}
	}

	// private void cancelTimers() {
	// for (Timer timer : timerService.getTimers()) {
	// timer.cancel();
	// }
	// }
}
