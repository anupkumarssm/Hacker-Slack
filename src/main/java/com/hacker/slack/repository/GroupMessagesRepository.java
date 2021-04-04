package com.hacker.slack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacker.slack.entity.GroupMessages;


public interface GroupMessagesRepository extends JpaRepository<GroupMessages, Integer>{

}
