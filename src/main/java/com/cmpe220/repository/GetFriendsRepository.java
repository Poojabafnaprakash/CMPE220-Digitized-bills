package com.cmpe220.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cmpe220.model.Bill;
import com.cmpe220.model.Friend;
import com.cmpe220.model.User;

//repository is used to fetch data from DB. Using JPA: in the model, specify it at @entity. 
// This enables the model to be a table in the DB.
// features Hibernate

public interface GetFriendsRepository extends CrudRepository<Friend, Integer> {
	 @Query("SELECT f FROM Friend f WHERE f.userId = :userId")
	  public List<Friend> findFriends(@Param("userId") User userId);
}
