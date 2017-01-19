package it.unipv.payroll.model;

import java.io.Serializable;

public interface ICredentials extends Serializable {

	public String getCode();

	public void setCode(String code);

	public String getPassword();

	public void setPassword(String password);

}