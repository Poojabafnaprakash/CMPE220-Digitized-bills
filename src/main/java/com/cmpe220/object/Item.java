package com.cmpe220.object;

import java.util.List;

import com.cmpe220.model.User;

public class Item {
	String itemCode;
	String itemDescription;
	Double itemAmt;
	List<User> splitIds;
	User paidBy;
//	List<SplitFriendsDetails> splitFriendsDetails;
	
	public Item() {
		super();
	}
	
	
	public Item(String itemCode, String itemDescription, Double itemAmt) {
		super();
		this.itemCode = itemCode;
		this.itemDescription = itemDescription;
		this.itemAmt = itemAmt;
		//this.splitFriendsDetails = splitFriendsDetails;
	}


	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public Double getItemAmt() {
		return itemAmt;
	}
	public void setItemAmt(Double itemAmt) {
		this.itemAmt = itemAmt;
	}


	public List<User> getSplitIds() {
		return splitIds;
	}


	public void setSplitIds(List<User> splitIds) {
		this.splitIds = splitIds;
	}


	public User getPaidBy() {
		return paidBy;
	}


	public void setPaidBy(User paidBy) {
		this.paidBy = paidBy;
	}
	
}
