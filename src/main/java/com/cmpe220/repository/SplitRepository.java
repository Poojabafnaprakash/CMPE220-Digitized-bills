package com.cmpe220.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import java.sql.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cmpe220.model.SplitReceipt;
import com.cmpe220.model.User;
import com.cmpe220.object.MonthlyExpen;

public interface SplitRepository extends CrudRepository<SplitReceipt, Integer> {

	@Query("SELECT SUM(b.amount) FROM SplitReceipt b WHERE b.userId = :userId and b.dateCreated >= :date1 and b.dateCreated <= :date2")
	public Integer findMonthlyExpen(@Param("userId") User userId,
			@Param("date1") Date date1, @Param("date2") Date date2);
	
	@Query("SELECT SUM(b.amount),MONTH(dateCreated) FROM SplitReceipt b WHERE  b.userId = :userId and YEAR(dateCreated) ='2017' GROUP BY MONTH(dateCreated)")
	public String[] findMonthlyExpenYear(@Param("userId") User userId);
			//,@Param("year") String year);

	@Query("SELECT SUM(b.amount) FROM SplitReceipt b WHERE b.paidBy = :userId")
	public Integer findOwedAmount(@Param("userId") User userId);

	@Query("SELECT b FROM SplitReceipt b WHERE b.paidBy = :userId")
	public List<SplitReceipt> findOwedDetails(@Param("userId") User userId);
}
