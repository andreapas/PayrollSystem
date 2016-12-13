package it.unipv.payroll.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.payroll.controller.TransactionsInfoController;
import it.unipv.payroll.model.TransactionsInfo;

@Named
@SessionScoped
public class InsertHoursBean implements Serializable{

	
	@Inject TransactionsInfoController tiController;

	private TransactionsInfo tiInfo;
	private String id;
	private String hours;
	
	@PostConstruct
	public void init() {
		tiInfo = new TransactionsInfo();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public TransactionsInfo getTiInfo() {
		return tiInfo;
	}
	public void setTiInfo(TransactionsInfo tiInfo) {
		this.tiInfo = tiInfo;
	}
	public String addHours(){
		tiInfo.setCode(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
		tiInfo.setInfo(" worked "+hours+" receipt id: "+id);
		String answer=tiController.add(tiInfo);
		return answer;
	}
	
	
	
	
}
