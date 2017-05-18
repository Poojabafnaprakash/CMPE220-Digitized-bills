package com.cmpe220.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe220.model.UserGroups;
import com.cmpe220.repository.UserGroupsRepository;

@Service
public class UserGroupService {

	@Autowired
	private UserGroupsRepository userGroupRepository;

	public UserGroups saveFriends(UserGroups userGroups) {
		return userGroupRepository.save(userGroups);

	}
}
