package it.unipv.payroll.model;

import java.util.List;

public interface IUnion {

	public List<Employee> getAssociates();

	public void setUnionName(String unionName);

	public void setWeeklyRate(float weeklyRate);

	public String getUnionName();

	public float getWeeklyRate();

}