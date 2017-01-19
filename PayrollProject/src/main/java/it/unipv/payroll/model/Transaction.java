package it.unipv.payroll.model;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type")
public abstract class Transaction implements ITransaction {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private Date date;
	private float amount;
	private String info;
	private boolean executed;
	@Override
	public Date getDate() {
		return date;
	}
	@Override
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public float getAmount() {
		return amount;
	}
	@Override
	public void setAmount(float amount) {
		this.amount = amount;
	}
	@Override
	public String getInfo() {
		return info;
	}
	@Override
	public void setInfo(String info) {
		this.info = info;
	}
	@Override
	public boolean isExecuted() {
		return executed;
	}
	@Override
	public void setExecuted(boolean executed) {
		this.executed = executed;
	}
	@Override
	public int getId() {
		return id;
	}

}
