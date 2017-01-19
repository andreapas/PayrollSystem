package it.unipv.payroll.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

public interface ITransaction extends Serializable {

	public Date getDate();

	public void setDate(Date date);

	public float getAmount();

	public void setAmount(float amount);

	public int getId();

	public String getInfo();

	public void setInfo(String info);

	public boolean isExecuted();

	public void setExecuted(boolean executed);

}