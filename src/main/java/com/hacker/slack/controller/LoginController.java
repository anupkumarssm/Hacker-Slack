package com.hacker.slack.controller;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hacker.slack.entity.Contacts;
import com.hacker.slack.entity.UserProfile;
import com.hacker.slack.entity.UserRole;
import com.hacker.slack.entity.Users;
import com.hacker.slack.repository.ContactsRepository;
import com.hacker.slack.repository.UserProfileRepository;
import com.hacker.slack.repository.UserRepository;
import com.hacker.slack.repository.UserRoleRepository;

@Controller
public class LoginController {
	@Autowired
	UserProfileRepository userProfileRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@Autowired
	ContactsRepository contactsRepository;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping(value = "/")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/login")
	public String login1(HttpServletRequest req, Model model) {
		model.addAttribute("status", req.getSession().getAttribute("status"));
		req.getSession().removeAttribute("status");
		return "login";
	}

	@RequestMapping(value = "/accessdenied")
	public String accessdenied(HttpServletRequest req, Model model) {
		return "login";
	}

	@RequestMapping("/logOut")
	public String getLogoutPage(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Logout");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null)
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		return "redirect:/login";
	}

	@RequestMapping(value = "signup")
	public String registration(HttpServletRequest req, UserProfile profile) {
		UserProfile up = userProfileRepository.findByUsername(profile.getMobile().trim());
		if(up == null) {
			String username=profile.getMobile().trim();
			 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			 String currentTime=sdf.format(timestamp);
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
			String encodedPassword = encoder.encode(profile.getMobile());
			/* User Profile */
			profile.setTimestamp(currentTime);
			profile.setUsername(username);
			userProfileRepository.save(profile);
			/* Users */
			Users user = new Users(profile.getMobile(), encodedPassword, 1);
			user = userRepository.save(user);
			/* Users Role */
			UserRole userRole = new UserRole(user.getUsername(), profile.getUserrole());
			userRoleRepository.save(userRole);
			
			Contacts contacts = new Contacts();
			contacts.setFullname(profile.getFullname());
			contacts.setMobile(profile.getMobile());
			contacts.setTimestamp(currentTime);
			contacts.setUsername(username);
			contactsRepository.save(contacts);
			
			
			req.getSession().setAttribute("status", "1");
		}
		return "redirect:/login";
	}

}
