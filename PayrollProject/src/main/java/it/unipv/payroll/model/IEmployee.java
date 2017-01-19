package it.unipv.payroll.model;

import java.util.List;

public interface IEmployee {

	public String getCode();

	public void setCode(String code);

	public String getName();

	public void setName(String name);

	public String getSurname();

	public void setSurname(String surname);

	public String getEmail();

	public void setEmail(String email);

	public IUnion getUnion();

	public void setUnion(IUnion union);

	public String getPayment_method();

	public void setPayment_method(String payment_method);

	public String getRole();

	public void setRole(String role);

	public String getAddress();

	public void setAddress(String address);

	public List<ITransaction> getCharges();

	public void setCharges(List<ITransaction> transactions);

	public String getPayment_method_details();

	public void setPayment_method_details(String payment_method_details);

}