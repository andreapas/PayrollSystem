package it.unipv.payroll.model;

import java.util.List;

public interface IUnion {

	List<Employee> getAssociates();

	void setUnionName(String unionName);

	void setWeeklyRate(float weeklyRate);

	String getUnionName();

	float getWeeklyRate();

}