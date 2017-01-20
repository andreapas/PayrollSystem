package it.unipv.payroll.view.payers_factory;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TimerService;
import javax.inject.Inject;

@Stateless
public class AutoPayday {
	
	@Inject
	IPayerFactory payerSel;

	@Resource
	TimerService timerService;

	@Schedule(dayOfWeek = "Fri", hour = "8", minute = "30")
	// @Schedule(hour = "16", minute = "33", second = "30")
	public HashMap<String, Float> weeklyPay() {
		HashMap<String, Float> total = 		payerSel.getPayerFromType("Weekly").pay();
		return total;
	}

	@Schedule(dayOfMonth = "Last", hour = "8", minute = "30")
	// @Schedule(hour = "16", minute = "33", second = "35")
	public HashMap<String, Float> monthlyPay() {
		HashMap<String, Float> total = 		payerSel.getPayerFromType("Monthly").pay();
		return total;
	}

}
