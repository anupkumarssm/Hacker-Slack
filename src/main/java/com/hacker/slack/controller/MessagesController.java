package com.hacker.slack.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hacker.slack.dao.MessagingDao;
import com.hacker.slack.entity.Contacts;
import com.hacker.slack.entity.GroupUsers;
import com.hacker.slack.entity.Groups;
import com.hacker.slack.repository.ContactsRepository;
import com.hacker.slack.repository.GroupMessagesRepository;
import com.hacker.slack.repository.GroupUsersRepository;
import com.hacker.slack.repository.GroupsRepository;
import com.hacker.slack.repository.UserProfileRepository;

@Controller
public class MessagesController {

	@Autowired
	ContactsRepository contactsRepository;
	@Autowired
	MessagingDao messagingDao;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	GroupsRepository groupsRepository;

	@Autowired
	GroupMessagesRepository groupMessagesRepository;

	@Autowired
	GroupUsersRepository groupUsersRepository;

	@Autowired
	UserProfileRepository userProfileRepository;

	@RequestMapping(value = "/home")
	public String home(HttpServletRequest req, Model model) {
		if (req.getUserPrincipal() == null)
			return "redirect:login";
		String username = req.getUserPrincipal().getName();
		model.addAttribute("getAllContacts", contactsRepository.findByUsername(username));
		model.addAttribute("getGroupList", messagingDao.getGroupList(username));
		req.getSession().setAttribute("userDetials", userProfileRepository.findByUsername(username));
		return "home";
	}

	@RequestMapping(value = "/createGroup")
	public String createGroup(HttpServletRequest req, Model model, Groups groups) {
		String username = req.getUserPrincipal().getName();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String currentTime = sdf.format(timestamp);
		groups.setCreatedBy(username);
		groups.setTimestamp(currentTime);
		groups = groupsRepository.save(groups);
		String mobile[] = req.getParameterValues("mobile");
		for (int i = 0; i < mobile.length; i++) {
			GroupUsers groupUsers = new GroupUsers();
			groupUsers.setGroupId(groups.getId());
			groupUsers.setMobile(mobile[i]);
			groupUsersRepository.save(groupUsers);
		}
		model.addAttribute("getAllContacts", contactsRepository.findByUsername(username));
		return "redirect:home";
	}

	@RequestMapping(value = "/group-messages")
	public String groupMessages(HttpServletRequest req, Model model) {
		if (req.getUserPrincipal() == null)
			return "redirect:login";
		String username = req.getUserPrincipal().getName();
		model.addAttribute("groupId", req.getParameter("group-id"));
		model.addAttribute("groupName", req.getParameter("group-name"));
		model.addAttribute("username", username);
		model.addAttribute("getAllContacts", contactsRepository.findByUsername(username));
		model.addAttribute("getGroupList", messagingDao.getGroupList(username));
		req.getSession().setAttribute("userDetials", userProfileRepository.findByUsername(username));
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		messagingDao.updateTime(username, sdf.format(timestamp));
		return "groupMessage";
	}

	@RequestMapping(value = "/getGroupMessage")
	public String getDirectMessage(HttpServletRequest req, Model model) {
		String username = req.getUserPrincipal().getName();
		model.addAttribute("username", username);
		String groupId = req.getParameter("groupId");
		String groupName = req.getParameter("groupName");
		model.addAttribute("groupMessageData", messagingDao.getGroupMessageData(groupId, "1", username));
		model.addAttribute("groupId", groupId);
		model.addAttribute("groupName", groupName);
		return "getGroupMessage";
	}

	@RequestMapping(value = "/contacts")
	public String contacts(HttpServletRequest req, Model model) {
		if (req.getUserPrincipal() == null)
			return "redirect:login";
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String username = req.getUserPrincipal().getName();
		model.addAttribute("getAllContacts", contactsRepository.findByUsername(username));
		messagingDao.updateTime(username, sdf.format(timestamp));
		req.getSession().setAttribute("userDetials", userProfileRepository.findByUsername(username));
		return "contacts";
	}

	@RequestMapping(value = "/addContacts")
	public String addContacts(HttpServletRequest req, Contacts contacts) {
		String username = req.getUserPrincipal().getName();
		Contacts c = contactsRepository.findByMobileAndUsername(contacts.getMobile().trim(), username);
		if (c == null) {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String currentTime = sdf.format(timestamp);
			contacts.setTimestamp(currentTime);
			contacts.setUsername(username);
			contactsRepository.save(contacts);
		}
		return "redirect:contacts";
	}
}
