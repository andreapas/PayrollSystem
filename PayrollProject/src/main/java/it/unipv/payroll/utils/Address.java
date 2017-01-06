package it.unipv.payroll.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Address {

	private String street;
	private int number;
	private int cap;
	private String municipality;
	private String districtCode;
	private List<String> districtCodeList = new ArrayList<String>(Arrays.asList("AG","AL","AN","AO","AQ","AR","AP","AT","AV","BA","BT","BL","BN","BG","BI","BO","BZ","BS","BR","CA","CL","CB","CI","CE","CT","CZ","CH","CO","CS","CR","KR","CN","EN","FM","FE","FI","FG","FC","FR","GE","GO","GR","IM","IS","SP","LT","LE","LC","LI","LO","LU","MC","MN","MS","MT","VS","ME","MI","MO","MB","NA","NO","NU","OG","OT","OR","PD","PA","PR","PV","PG","PU","PE","PC","PI","PT","PN","PZ","PO","RG","RA","RC","RE","RI","RN","RM","RO","SA","SS","SV","SI","SR","SO","TA","TE","TR","TO","TP","TN","TV","TS","UD","VA","VE","VB","VC","VR","VV","VI"));

	public Address() {
		street="";
		number=0;
		cap=0;
		municipality="";
		districtCode="";
	}
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getCap() {
		return cap;
	}
	public void setCap(int cap) {
		this.cap = cap;
	}
	public String getMunicipality() {
		return municipality;
	}
	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public List<String> getDistrictCodeList() {
		return districtCodeList;
	}
	
	@Override
	public String toString() {
		return street+" "+number+" "+cap+" "+municipality+" ("+districtCode+") "+"Italy (IT)";
	}
}
