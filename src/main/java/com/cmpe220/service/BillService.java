package com.cmpe220.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe220.model.Bill;
import com.cmpe220.model.User;
import com.cmpe220.repository.BillRepository;
import com.cmpe220.repository.UserRepository;

@Service
public class BillService {

	@Autowired
	private BillRepository billRepository;

	public Bill addBill(Bill bill) {
		return billRepository.save(bill);
	}

	public Iterable<Bill> getBills() {
		return billRepository.findAll();
	}

	public List<Bill> findBills(User user) {
		return billRepository.findBills(user);
	}

}
