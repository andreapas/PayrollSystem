package it.unipv.payroll.view;

import java.io.Serializable;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.model.Employee;

@Named
@SessionScoped
@Stateful
public class RowListenerBean implements Serializable{

	@Inject EmployeeController emController;
	
	
	public void onRowEdit(RowEditEvent event) {

		Employee employee = ((Employee) event.getObject());
		String answer = emController.update(employee);
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			if (answer.equals("Operation completed successfully.")) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
						"Employee " + employee.getName() + " " + employee.getSurname() + " updated successfully!"));
			} else {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
								"Something has gone wrong while trying to update employee " + employee.getName() + " "
										+ employee.getSurname() + ". The complete message is " + answer));
			}
		} catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}
	}

	public void onRowCancel(RowEditEvent event) {
		Employee employee = ((Employee) event.getObject());
		try {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edit Cancelled",
					"Employee " + employee.getName() + " " + employee.getSurname() + " has not been edited.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}
	}
	
}
