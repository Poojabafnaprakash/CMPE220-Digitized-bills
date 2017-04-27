package com.cmpe220.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table
public class Items {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	int id;

	@Column(name = "item_description")
	String itemDescription;

	@Column(name = "item_price")
	Double itemPrice;

	@ManyToOne
	@JoinColumn(name = "bill_id")
	private Bill billId;

	@Transient
	List<User> splitIds;

	@Transient
	User paidBy;

	public Items() {

	}

	public Items(int itemId, String itemDescription, Double itemPrice,
			Bill billId) {
		super();
		this.id = itemId;
		this.itemDescription = itemDescription;
		this.itemPrice = itemPrice;
		this.billId = billId;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Bill getBillId() {
		return billId;
	}

	public void setBillId(Bill billId) {
		this.billId = billId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
