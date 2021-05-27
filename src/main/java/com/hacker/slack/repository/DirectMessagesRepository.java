package com.hacker.slack.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;

import com.hacker.slack.entity.DirectMessages; 


public interface DirectMessagesRepository extends JpaRepository<DirectMessages, Integer>{
}
