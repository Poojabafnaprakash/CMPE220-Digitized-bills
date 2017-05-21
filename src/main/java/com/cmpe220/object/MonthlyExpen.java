package com.cmpe220.object;

public class MonthlyExpen {
	
	String expen;
	String month;
	public MonthlyExpen(String expen, String month) {
		super();
		this.expen = expen;
		this.month = month;
	}

	public String getExpen() {
		return expen;
	}
	public void setExpen(String expen) {
		this.expen = expen;
	}
	public String getMonth() {
		return month;
	}
	public MonthlyExpen() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setMonth(String month) {
		this.month = month;
	}

}
