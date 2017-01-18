package it.unipv.payroll.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import it.unipv.payroll.model.Employee;

public class ReportCreator {

	public String generateFile(List<Employee> emList, HashMap<String, Float> earnings, String type) {
		String nameTitle="executed"+type+"Payments.txt";
		
		String formatStr = "%-15s %-15s %-15s %-15s %-15s %-15s%n";
		try {
			String toWrite = "Code\tName\tSurname\tAmount\tPayment Method\tPayments Details\n";
			toWrite=String.format(formatStr, "Code","Name","Surname","Amount","Payment Method","Payments Details");
			String tmp;
			for (Employee employee : emList) {
				if (employee.getPayment_method_details()==null) 
					employee.setPayment_method_details("");
				tmp = String.format(formatStr, employee.getCode() , employee.getName() , employee.getSurname() , earnings.get(employee.getCode()) , employee.getPayment_method(), employee.getPayment_method_details());
				toWrite+=tmp;
			}
		    Files.write(Paths.get(nameTitle), toWrite.getBytes());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nameTitle;
	}
}
