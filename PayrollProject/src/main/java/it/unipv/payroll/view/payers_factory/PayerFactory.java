package it.unipv.payroll.view.payers_factory;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class PayerFactory implements IPayerFactory {

	@Inject private WeeklyTransactionsBean week;
	@Inject private MonthlyTransactionsBean month;
	
	
	
	@Override
	public IPayer getPayerFromType(String type){
		if (type==null||type.isEmpty()) {
			return null;
		}
		if (type.equals("Weekly")) {
			return getWeek();
		}else if (type.equals("Monthly")) {
			return getMonth();
		}
		return null;
	}
	private IPayer getWeek() {
		return week;
	}

	private IPayer getMonth() {
		return month;
	}	
}
