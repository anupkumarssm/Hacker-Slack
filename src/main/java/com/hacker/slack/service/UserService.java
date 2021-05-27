package com.hacker.slack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacker.slack.entity.Users;
import com.hacker.slack.repository.UserRepository;
 

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public Users save(Users user) {
		return userRepository.saveAndFlush(user);
	}

	public Users update(Users user) {
		return userRepository.save(user);
	}

	public Users find(String userName) {
		return userRepository.findOneByUsername(userName);
	}

	/*public Users find(Long id) {
		return userRepository.findOne(id);
	}*/
}
