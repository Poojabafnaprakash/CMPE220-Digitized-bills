package com.cmpe220.object;

import java.util.List;

import com.cmpe220.model.Items;
import com.cmpe220.model.User;

public class JsonRequestWrapper {

	String billPath;
	int userID;
	Double tax;
	Double total;
	String billName;
	List<Items> items;
	User paidBy;
	List<User> splitIds;
	String flag;

	public String getBillPath() {
		return billPath;
	}

	public void setBillPath(String billPath) {
		this.billPath = billPath;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}

	public List<Items> getItems() {
		return items;
	}

	public void setItems(List<Items> items) {
		this.items = items;
	}

	public User getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(User paidBy) {
		this.paidBy = paidBy;
	}

	public List<User> getSplitIds() {
		return splitIds;
	}

	public void setSplitIds(List<User> splitIds) {
		this.splitIds = splitIds;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
