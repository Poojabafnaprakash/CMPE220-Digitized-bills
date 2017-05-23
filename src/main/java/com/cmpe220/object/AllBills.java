package com.cmpe220.object;

import java.util.List;

import com.cmpe220.model.User;

public class AllBills {
	String billName;
	String flag;
	int total;
	
	public AllBills() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public AllBills(String billName, String flag, int total) {
		super();
		this.billName = billName;
		this.flag = flag;
		this.total = total;
	}

	
	
	
	
}
