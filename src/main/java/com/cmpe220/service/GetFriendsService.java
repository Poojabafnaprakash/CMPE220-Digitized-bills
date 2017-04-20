package com.cmpe220.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmpe220.model.Friend;
import com.cmpe220.repository.GetFriendsRepository;

@Service
public class GetFriendsService {
	
	//Service is created to create a instance.
	
	@Autowired
	private GetFriendsRepository getFriendsRepository;
	
	@ResponseBody
	public List<Friend> getAllFriends(){
		//getFriendsRepository.save(friends);
		List<Friend> friends = new ArrayList<>();
		getFriendsRepository.findAll()
		.forEach(friends::add);
		return friends;
		
	}

}
