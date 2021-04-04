package com.hacker.slack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacker.slack.entity.GroupUsers;


public interface GroupUsersRepository extends JpaRepository<GroupUsers, Integer>{

}
