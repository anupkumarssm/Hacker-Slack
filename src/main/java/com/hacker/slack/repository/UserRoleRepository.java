package com.hacker.slack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacker.slack.entity.UserRole;
 

public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{

}
