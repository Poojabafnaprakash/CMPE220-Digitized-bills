package com.cmpe220.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe220.model.SplitReceipt;
import com.cmpe220.model.User;
import com.cmpe220.repository.SplitRepository;

@Service
public class SplitService {

	@Autowired
	private SplitRepository splitRepository;
	
	public SplitReceipt addItems(SplitReceipt items){
		return splitRepository.save(items);
	}
	
	public Integer findMonthlyExpen(User user){
		return splitRepository.findMonthlyExpen(user);
	}
	
}
