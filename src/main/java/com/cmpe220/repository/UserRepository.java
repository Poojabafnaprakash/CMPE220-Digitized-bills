package com.cmpe220.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cmpe220.model.Bill;
import com.cmpe220.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	 @Query("SELECT u FROM User u WHERE u.id in (:userId)")
	  public List<User> findUsers(@Param("userId") List<Integer> userId);
	
}
