package it.unipv.payroll.model;

import java.io.Serializable;

public interface ICredentials extends Serializable {

	String getCode();

	void setCode(String code);

	String getPassword();

	void setPassword(String password);

}