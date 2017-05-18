package com.cmpe220.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe220.model.Groups;
import com.cmpe220.model.User;
import com.cmpe220.repository.GroupRepository;

@Service
public class GroupService {

	@Autowired
	private GroupRepository groupRepository;

	public Groups createGroup(Groups groups) {
		return groupRepository.save(groups);

	}
	
	public List<Groups> getAllGroups() {
		List<Groups> groups = new ArrayList<>();
		groupRepository.findAll()
		.forEach(groups::add);
		return groups;

	}

}
