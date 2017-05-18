package com.cmpe220.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="split_receipt")
public class SplitReceipt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="receipt_id")
	int receiptId;
	
	@OneToOne
	@JoinColumn(name="user_id")
	User userId;
	
	@OneToOne
	@JoinColumn(name="paid_id")
	User paidBy;
	
	@OneToOne
	@JoinColumn(name="bill_id")
	Bill billId;
	
//	@OneToOne
//	@JoinColumn(name="friend_id")
//	Friend friendId;
	
	@OneToOne
	@JoinColumn(name="item_id")
	Items itemId;
	
	@Column(name="amount")
	Double amount;
	
	@Column(name="date_created")
	Date dateCreated;
	
	
	public SplitReceipt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SplitReceipt(int receiptId, User userId, Bill billId, Items itemId, Double amount, User paidBy, Date dateCreated) {
		super();
		this.receiptId = receiptId;
		this.userId = userId;
		this.billId = billId;
		this.paidBy = paidBy;
		//this.friendId = friendId;
		this.itemId = itemId;
		this.amount = amount;
		this.dateCreated=dateCreated;
	}

	public int getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Bill getBillId() {
		return billId;
	}

	public void setBillId(Bill billId) {
		this.billId = billId;
	}

//	public Friend getFriendId() {
//		return friendId;
//	}
//
//	public void setFriendId(Friend friendId) {
//		this.friendId = friendId;
//	}

	public Items getItemId() {
		return itemId;
	}

	public void setItemId(Items itemId) {
		this.itemId = itemId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public User getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(User paidBy) {
		this.paidBy = paidBy;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	

}
