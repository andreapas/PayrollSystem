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
import it.unipv.payroll.model.IUnion;
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

	public IUnion getUnion() {
		return union;
	}

	public void setUnion(Union union) {
		this.union = union;
	}

	public void addUnion() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			unionsController.add(union);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
					"The union " + union.getUnionName() + " has been successfully added"));
			unions = unionsController.getUnionsList();
		} catch (Exception e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
							"Something has gone wrong while trying to add " + union.getUnionName()
									+ ". The complete message is: " + e.getMessage()));

		}

	}

	// private void clearUnion(){
	// union.setUnionName("");
	// union.setWeeklyRate(0);
	// }
	public void removeUnion() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			unionsController.remove(fireUnionName);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
					"The union " + fireUnionName + " has been successfully removed"));
			fireUnionName = "";
			unions = unionsController.getUnionsList();
		} catch (Exception e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
							"Something has gone wrong while trying to remove " + fireUnionName
									+ ". The complete message is: " + e.getMessage()));

		}
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
		FacesContext context = FacesContext.getCurrentInstance();

		FacesMessage message;
		try {
			unionsController.update(union);
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
					"The Union " + union.getUnionName() + " has been updated successfully!");

			context.addMessage(null, message);

			unions = unionsController.getUnionsList();
			fireUnionName = "";
		} catch (Exception e) {
			message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!",
					"Something has gone wrong while trying to update the Union details. The complete message is: "
							+ e.getMessage());

		}
	}
	// public void clearSelection(){
	// fireUnionName="";
	// union=new Union();
	// unions= unionsController.getUnionsList();
	// }

}
