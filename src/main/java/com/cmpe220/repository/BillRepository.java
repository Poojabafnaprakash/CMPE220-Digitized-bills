package com.cmpe220.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cmpe220.model.Bill;
import com.cmpe220.model.User;


public interface BillRepository  extends CrudRepository<Bill, Integer> {
	
	 @Query("SELECT b FROM Bill b WHERE b.userId = :userId")
	  public List<Bill> findBills(@Param("userId") User userId);

}

