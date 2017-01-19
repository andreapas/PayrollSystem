package it.unipv.payroll.mediator;

import javax.inject.Inject;
import javax.inject.Singleton;

import it.unipv.payroll.controller.ChargesController;
import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.controller.FullTimeController;
import it.unipv.payroll.controller.PartTimeController;
import it.unipv.payroll.controller.SalaryController;
import it.unipv.payroll.controller.SalesController;
import it.unipv.payroll.controller.SessionManagementController;
import it.unipv.payroll.controller.TimeCardController;
import it.unipv.payroll.controller.UnionsController;
import it.unipv.payroll.utils.Mail;
import it.unipv.payroll.view.MonthlyTransactionsBean;
import it.unipv.payroll.view.WeeklyTransactionsBean;

@Singleton
public class ControllerMediator implements IControllerMediator {

	@Inject
	EmployeeController emController;
	@Inject
	PartTimeController ptController;
	@Inject
	FullTimeController ftController;

	@Inject
	SalesController saController;
	@Inject
	TimeCardController tcController;
	@Inject
	ChargesController cController;

	@Inject
	SessionManagementController smController;
	@Inject
	UnionsController unController;

	@Inject
	WeeklyTransactionsBean weekBean;
	@Inject
	MonthlyTransactionsBean monthBean;

	@Inject
	SalaryController salaryController;
	@Inject
	Mail mailer;
	private static IControllerMediator med;
	
	public ControllerMediator() {
	}

	public static IControllerMediator getMed() {
		return med;
	}
	
	/* (non-Javadoc)
	 * @see it.unipv.payroll.mediator.IControllerMediator#getEmController()
	 */
	@Override
	public EmployeeController getEmController() {
		return emController;
	}

	/* (non-Javadoc)
	 * @see it.unipv.payroll.mediator.IControllerMediator#getPtController()
	 */
	@Override
	public PartTimeController getPtController() {
		return ptController;
	}

	/* (non-Javadoc)
	 * @see it.unipv.payroll.mediator.IControllerMediator#getFtController()
	 */
	@Override
	public FullTimeController getFtController() {
		return ftController;
	}

	/* (non-Javadoc)
	 * @see it.unipv.payroll.mediator.IControllerMediator#getSaController()
	 */
	@Override
	public SalesController getSaController() {
		return saController;
	}

	/* (non-Javadoc)
	 * @see it.unipv.payroll.mediator.IControllerMediator#getTcController()
	 */
	@Override
	public TimeCardController getTcController() {
		return tcController;
	}

	/* (non-Javadoc)
	 * @see it.unipv.payroll.mediator.IControllerMediator#getcController()
	 */
	@Override
	public ChargesController getcController() {
		return cController;
	}

	/* (non-Javadoc)
	 * @see it.unipv.payroll.mediator.IControllerMediator#getSmController()
	 */
	@Override
	public SessionManagementController getSmController() {
		return smController;
	}

	/* (non-Javadoc)
	 * @see it.unipv.payroll.mediator.IControllerMediator#getUnController()
	 */
	@Override
	public UnionsController getUnController() {
		return unController;
	}

	/* (non-Javadoc)
	 * @see it.unipv.payroll.mediator.IControllerMediator#getWeekBean()
	 */
	@Override
	public WeeklyTransactionsBean getWeekBean() {
		return weekBean;
	}

	/* (non-Javadoc)
	 * @see it.unipv.payroll.mediator.IControllerMediator#getMonthBean()
	 */
	@Override
	public MonthlyTransactionsBean getMonthBean() {
		return monthBean;
	}

	/* (non-Javadoc)
	 * @see it.unipv.payroll.mediator.IControllerMediator#getSalaryController()
	 */
	@Override
	public SalaryController getSalaryController() {
		return salaryController;
	}
	
	/* (non-Javadoc)
	 * @see it.unipv.payroll.mediator.IControllerMediator#getMailer()
	 */
	@Override
	public Mail getMailer() {
		return mailer;
	}
}
