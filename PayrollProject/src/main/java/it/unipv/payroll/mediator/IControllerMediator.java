package it.unipv.payroll.mediator;

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

public interface IControllerMediator {

	public EmployeeController getEmController();

	public PartTimeController getPtController();

	public FullTimeController getFtController();

	public SalesController getSaController();

	public TimeCardController getTcController();

	public ChargesController getcController();

	public SessionManagementController getSmController();

	public UnionsController getUnController();

	public WeeklyTransactionsBean getWeekBean();

	public MonthlyTransactionsBean getMonthBean();

	public SalaryController getSalaryController();

	public Mail getMailer();

}