package com.cmpe220.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="bill")
public class Bill implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	int id;
	
	@Column(name="tax")
	Double tax;
	
	@Column(name="total")
	Double total;
	
	@Column(name="bill_path")
	String billPath;
	
	@Column(name="bill_name")
	String billName;
	
	@Column(name="flag")
	String totOrItem;

	@OneToOne 
	@JoinColumn(name="user_id")
	User userId;
	
	@OneToMany(mappedBy="billId") @JsonIgnore
	private Set<Items> items;
	
	public Bill( Double total, String billName, String totOrItem) {
		  this.total = total;
		  this.billName = billName;
		  this.totOrItem= totOrItem;
		 }
	
	

	public Bill() {
		super();
		// TODO Auto-generated constructor stub
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getBillPath() {
		return billPath;
	}

	public void setBillPath(String billPath) {
		this.billPath = billPath;
	}

	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}

	public String getTotOrItem() {
		return totOrItem;
	}

	public void setTotOrItem(String totOrItem) {
		this.totOrItem = totOrItem;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Set<Items> getItems() {
		return items;
	}

	public void setItems(Set<Items> items) {
		this.items = items;
	}

	public Bill(int id, Double tax, Double total, String billPath, String billName, String totOrItem, User userId,
			Set<Items> items) {
		super();
		this.id = id;
		this.tax = tax;
		this.total = total;
		this.billPath = billPath;
		this.billName = billName;
		this.totOrItem = totOrItem;
		this.userId = userId;
		this.items = items;
	}

	

}
