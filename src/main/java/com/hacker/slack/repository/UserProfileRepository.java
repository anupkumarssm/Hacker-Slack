package com.hacker.slack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacker.slack.entity.UserProfile;
 

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer>{

	UserProfile findByUsername(String username);

}
