package com.cmpe220.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;

import java.sql.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cmpe220.model.SplitReceipt;
import com.cmpe220.model.User;

public interface SplitRepository extends CrudRepository<SplitReceipt, Integer> {

	@Query("SELECT COALESCE(SUM(b.amount),0) FROM SplitReceipt b WHERE b.userId = :userId and MONTH(dateCreated)= :month")
	public Integer findMonthlyExpen(@Param("userId") User userId,@Param("month") int month);

	@Query("SELECT COALESCE(SUM(b.amount),0),MONTH(dateCreated) FROM SplitReceipt b WHERE  b.userId = :userId and YEAR(dateCreated) ='2017' GROUP BY MONTH(dateCreated)")
	public String[] findMonthlyExpenYear(@Param("userId") User userId);

	@Query("SELECT COALESCE(SUM(b.amount),0) as amount,b.userId as user  FROM SplitReceipt b WHERE b.userId != :userId and b.paidBy = :userId GROUP BY b.userId")
	public List<Map<Double, User>> findOwedDetails(@Param("userId") User userId);

	@Query("SELECT COALESCE(SUM(b.amount),0) as amount,b.paidBy as user FROM SplitReceipt b WHERE b.userId = :userId and b.paidBy != :userId GROUP BY b.paidBy")
	public List<Map<Double,User>> findOweDetails(@Param("userId") User userId);
	
	@Query("SELECT COALESCE(SUM(b.amount),0) FROM SplitReceipt b WHERE b.paidBy != :userId and b.userId = :userId" )
	public Double findTotalYouOwe(@Param("userId") User userId);
	
	
	@Query("SELECT COALESCE(SUM(b.amount),0) FROM SplitReceipt b WHERE b.userId != :userId and b.paidBy = :userId ")
	public Double findTotalYouAreOwed(@Param("userId") User userId);
}
