package com.cmpe220.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="groups")
public class Groups {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	int id;
	
	@Column(name="group_name")
	String groupName;

	@Column(name="date_created")
	String dateCreated;
	
	@Column(name="date_updated")
	String dateUpdated;
	
	@Transient
	List<User> userIds;
	
	public Groups(){
		
	}
	
	public Groups(int id, String groupName, String dateCreated, String dateUpdated) {
		super();
		this.id = id;
		this.groupName = groupName;
		this.dateCreated = dateCreated;
		this.dateUpdated = dateUpdated;
	}

	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(String dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	

	public List<User> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<User> userIds) {
		this.userIds = userIds;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
