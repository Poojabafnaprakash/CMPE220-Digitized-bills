package com.cmpe220.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe220.model.Bill;
import com.cmpe220.model.Items;
import com.cmpe220.repository.BillRepository;
import com.cmpe220.repository.ItemsRepository;

@Service
public class ItemsService {
	@Autowired
	private ItemsRepository itemsRepository;
	
	public Items addItems(Items items){
		return itemsRepository.save(items);
	}
}
