package com.cmpe220.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cmpe220.model.Bill;
import com.cmpe220.model.SplitReceipt;
import com.cmpe220.model.User;

public interface SplitRepository extends CrudRepository<SplitReceipt, Integer> {
	
	@Query("SELECT SUM(b.amount) FROM SplitReceipt b WHERE b.userId = :userId")
	  public Integer findMonthlyExpen(@Param("userId") User userId);
	
	@Query("SELECT SUM(b.amount) FROM SplitReceipt b WHERE b.paidBy = :userId")
	  public Integer findOwedAmount(@Param("userId") User userId);
	
	@Query("SELECT b FROM SplitReceipt b WHERE b.paidBy = :userId")
	  public List<SplitReceipt> findOwedDetails(@Param("userId") User userId);
}
