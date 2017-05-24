package com.cmpe220.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe220.model.SplitReceipt;
import com.cmpe220.model.User;
import com.cmpe220.repository.SplitRepository;

@Service
public class SplitService {

	@Autowired
	private SplitRepository splitRepository;

	public SplitReceipt addItems(SplitReceipt items) {
		return splitRepository.save(items);
	}

	public Double findMonthlyExpen(User user, int month) {
		return splitRepository.findMonthlyExpen(user, month);
	}

	public String[] findMonthlyExpenYear(User user, String year) {
		return splitRepository.findMonthlyExpenYear(user);
	}

	
	public List<Map<Double,User>> findOweDetails(User user) {
		return splitRepository.findOweDetails(user);
	}

	public List<Map<Double,User>> findOwedDetails(User user) {
		return splitRepository.findOwedDetails(user);
	}

	public Double findTotalYouOwe(User user) {
		return splitRepository.findTotalYouOwe(user);
	}

	public Double findTotalYouAreOwed(User user) {
		return splitRepository.findTotalYouAreOwed(user);
	}
}
