package com.cmpe220.service;

import java.sql.Date;

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

	public int findMonthlyExpen(User user, Date date1,
			Date date2) {
		return splitRepository.findMonthlyExpen(user, date1, date2);
	}
	public String[] findMonthlyExpenYear(User user, String year) {
		return splitRepository.findMonthlyExpenYear(user);
	}
}
