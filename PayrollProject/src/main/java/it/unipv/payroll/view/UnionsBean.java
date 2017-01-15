package it.unipv.payroll.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.UnionsController;
import it.unipv.payroll.model.Union;

@Named
@SessionScoped
public class UnionsBean implements Serializable {

	@Inject
	UnionsController unionsController;

	private Union union;
	private String fireUnionName;

	private List<Union> unions;

	@PostConstruct
	public void init() {
		union = new Union();
		fireUnionName = new String();
		unions = unionsController.getUnionsList();

	}

	public Union getUnion() {
		return union;
	}

	public void setUnion(Union union) {
		this.union = union;
	}

	public String addUnion() {
		String answer = unionsController.add(union);
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			if (answer.equals("Operation completed successfully.")) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
						"The union " + union.getUnionName() + " has been successfully added"));
			} else {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
								"Something has gone wrong while trying to add " + union.getUnionName()
										+ ". The complete message is: " + answer));
			}
		} catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}
		unions = unionsController.getUnionsList();
		// clearUnion();
		return answer;

	}

	// private void clearUnion(){
	// union.setUnionName("");
	// union.setWeeklyRate(0);
	// }
	public String removeUnion() {
		String answer = unionsController.remove(fireUnionName);
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			if (answer.equals("Operation completed successfully.")) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
						"The union " + fireUnionName + " has been successfully removed"));
			} else {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
								"Something has gone wrong while trying to remove " + fireUnionName
										+ ". The complete message is: " + answer));
			}
		} catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}
		fireUnionName = "";
		unions = unionsController.getUnionsList();

		return answer;

	}

	public String getFireUnionName() {
		return fireUnionName;
	}

	public void setFireUnionName(String fireUnionName) {
		this.fireUnionName = fireUnionName;
	}

	public List<Union> getUnionsList() {
		return unions;
	}

	// TODO: to be tested
	public void updateUnion() {
		String answer = unionsController.update(union);
		try {
			FacesMessage message;
			if (answer.equals("Operation completed successfully.")) {
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
						"The Union " + union.getUnionName() + " has been updated successfully!");
			} else {
				message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
						"Something has gone wrong while trying to update the Union details. The complete message is: "
								+ answer);

			}
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, message);

		} catch (NullPointerException e) {
			System.out.println("Detected a null FacesContext: maybe this bean has been ran for testing.");
		}
		unions = unionsController.getUnionsList();
		fireUnionName = "";
	}
	// public void clearSelection(){
	// fireUnionName="";
	// union=new Union();
	// unions= unionsController.getUnionsList();
	// }

}
