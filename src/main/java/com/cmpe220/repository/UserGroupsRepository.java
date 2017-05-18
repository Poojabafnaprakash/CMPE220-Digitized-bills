package com.cmpe220.repository;

import org.springframework.data.repository.CrudRepository;

import com.cmpe220.model.Groups;
import com.cmpe220.model.UserGroups;

public interface UserGroupsRepository extends CrudRepository<UserGroups, Integer>{
	
}
