package com.cmpe220.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="users_groups")
public class UserGroups {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToOne
	@JoinColumn(name="user_id")
	User userId;
	
	@OneToOne
	@JoinColumn(name="id")
	Groups groups;
	
	
	public UserGroups() {
		super();
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public User getUserId() {
		return userId;
	}


	public void setUserId(User userId) {
		this.userId = userId;
	}


	public Groups getGroups() {
		return groups;
	}


	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	
	

}
