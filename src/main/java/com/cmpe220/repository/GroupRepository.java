package com.cmpe220.repository;

import org.springframework.data.repository.CrudRepository;

import com.cmpe220.model.Groups;

public interface GroupRepository extends CrudRepository<Groups, Integer> {

}
