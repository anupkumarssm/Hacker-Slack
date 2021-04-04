package com.hacker.slack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacker.slack.entity.Contacts;
 

public interface ContactsRepository extends JpaRepository<Contacts, Integer>{

	Contacts findByMobile(String toMobile);

	List<Contacts> findByUsername(String username);

	Contacts findByMobileAndUsername(String trim, String username);

}
