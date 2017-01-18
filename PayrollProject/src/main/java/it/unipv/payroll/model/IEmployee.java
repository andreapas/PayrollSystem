package it.unipv.payroll.model;

import java.util.List;

public interface IEmployee {

	String getCode();

	void setCode(String code);

	String getName();

	void setName(String name);

	String getSurname();

	void setSurname(String surname);

	String getEmail();

	void setEmail(String email);

	Union getUnion();

	void setUnion(Union union);

	String getPayment_method();

	void setPayment_method(String payment_method);

	String getRole();

	void setRole(String role);

	String getAddress();

	void setAddress(String address);

	List<Transactions> getTransactions();

	void setTransactions(List<Transactions> transactions);

	String getPayment_method_details();

	void setPayment_method_details(String payment_method_details);

}