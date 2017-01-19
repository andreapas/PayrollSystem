package it.unipv.payroll.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.IEmployee;

public class ReportCreator {

	public String generateFile(List<IEmployee> emList, HashMap<String, Float> earnings, String type) {
		DateFormat dateFormat= new SimpleDateFormat("ddMMyyyy");
		String date= dateFormat.format(new Date());
		Path path=Paths.get("./reports/executed"+type+"Payments"+date+".txt");
//		String nameTitle="src/main/java/resources/reports/executed"+type+"Payments.txt";
		
		String formatStr = "%-15s %-15s %-15s %-15s %-15s %-15s%n";
		try {
			Files.createDirectories(path.getParent());
			String toWrite = "Code\tName\tSurname\tAmount\tPayment Method\tPayments Details\n";
			toWrite=String.format(formatStr, "Code","Name","Surname","Amount","Payment Method","Payments Details");
			String tmp;
			for (IEmployee employee : emList) {
				if (employee.getPayment_method_details()==null) 
					employee.setPayment_method_details("");
				tmp = String.format(formatStr, employee.getCode() , employee.getName() , employee.getSurname() , earnings.get(employee.getCode()) , employee.getPayment_method(), employee.getPayment_method_details());
				toWrite+=tmp;
			}
			Files.createFile(path);
		    Files.write(path, toWrite.getBytes());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path.toString();
	}
}
