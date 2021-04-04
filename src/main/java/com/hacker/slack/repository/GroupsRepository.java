package com.hacker.slack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacker.slack.entity.Groups;
 

public interface GroupsRepository extends JpaRepository<Groups, Integer>{

}
