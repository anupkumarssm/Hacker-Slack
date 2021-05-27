package com.hacker.slack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacker.slack.entity.Users;
 

public interface UserRepository extends JpaRepository<Users,Integer>{

	Users findByUsername(String username);
	public Users findOneByUsername(String username);

}
