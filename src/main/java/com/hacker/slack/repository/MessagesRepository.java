package com.hacker.slack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacker.slack.entity.Messages;


public interface MessagesRepository extends JpaRepository<Messages, Integer>{

	Messages findByToUsername(String toMobile);

	List<Messages> findByUsername(String username);

	Messages findFirstByUsername(String username);

	Messages findByToMobile(String username);

	Messages findFirstByToUsername(String username);

}
