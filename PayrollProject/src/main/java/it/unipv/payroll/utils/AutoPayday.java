package it.unipv.payroll.utils;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TimerService;
import javax.inject.Inject;

import it.unipv.payroll.mediator.ControllerMediator;
import it.unipv.payroll.view.MonthlyTransactionsBean;
import it.unipv.payroll.view.WeeklyTransactionsBean;

@Stateless
public class AutoPayday {

//	@Inject
//	WeeklyTransactionsBean weekBean;
//	@Inject
//	MonthlyTransactionsBean monthBean;


	@Resource
	TimerService timerService;

	@Schedule(dayOfWeek = "Fri", hour = "8", minute = "30")
	// @Schedule(hour = "16", minute = "33", second = "30")
	public HashMap<String, Float> weeklyPay() {
		ControllerMediator.getMed().getWeekBean().addWeeklyUnionFee();
		 HashMap<String, Float>total=ControllerMediator.getMed().getWeekBean().pay();
		 return total;
	}

	@Schedule(dayOfMonth = "Last", hour = "8", minute = "30")
	// @Schedule(hour = "16", minute = "33", second = "35")
	public HashMap<String, Float> monthlyPay() {
		ControllerMediator.getMed().getMonthBean().addSalaries();
		 HashMap<String, Float>total=ControllerMediator.getMed().getMonthBean().pay();
		 return total;
	}

	

}
