package com.hacker.slack.controller;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.hacker.slack.dao.MessagingDao;
import com.hacker.slack.entity.Contacts;
import com.hacker.slack.entity.DirectMessages;
import com.hacker.slack.entity.GroupMessages;
import com.hacker.slack.entity.GroupUsers;
import com.hacker.slack.entity.Groups;
import com.hacker.slack.entity.Messages;
import com.hacker.slack.entity.Users;
import com.hacker.slack.repository.ContactsRepository;
import com.hacker.slack.repository.DirectMessagesRepository;
import com.hacker.slack.repository.GroupMessagesRepository;
import com.hacker.slack.repository.GroupUsersRepository;
import com.hacker.slack.repository.GroupsRepository;
import com.hacker.slack.repository.MessagesRepository;
import com.hacker.slack.repository.UserProfileRepository;
import com.hacker.slack.service.UserService;
import com.hacker.slack.utils.CustomErrorType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api")
public class HackerSlackAPIController {
	private static final Logger logger = LogManager.getLogger(HackerSlackAPIController.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	@Autowired
	ContactsRepository contactsRepository;
	@Autowired
	MessagingDao messagingDao;
	@Autowired
	MessagesRepository messagesRepository;
	@Autowired
	DirectMessagesRepository directMessagesRepository;
	@Autowired
	GroupsRepository groupsRepository;

	@Autowired
	GroupMessagesRepository groupMessagesRepository;

	@Autowired
	GroupUsersRepository groupUsersRepository;

	@Autowired
	UserProfileRepository userProfileRepository; 
	
	@Autowired
	private UserService userService;
	
	
	
	@CrossOrigin
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody Users newUser) {
		if (userService.find(newUser.getUsername()) != null) {
			logger.error("username Already exist " + newUser.getUsername());
			return new ResponseEntity(
					new CustomErrorType("user with username " + newUser.getUsername() + "already exist "),
					HttpStatus.CONFLICT);
		} 
		return new ResponseEntity<Users>(userService.save(newUser), HttpStatus.CREATED);
	}

	// this is the login api/service
	@CrossOrigin
	@RequestMapping("/login123")
	public Principal user(Principal principal) {
		logger.info("user logged "+principal);
		return principal;
	}
	
	@RequestMapping("/logintest")
	public void usertest(@ModelAttribute Users user) {
		System.out.println("user logged "+user.getUsername());
	}
	
	
	@RequestMapping("/logintest1")
	public Map<String,Object> usertest1(HttpServletRequest req) {
		String username=req.getParameter("username");
		System.out.println("user logged "+username);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("test", "Ok Done ");
		return map;
	}
	
	@RequestMapping ("/currentUser")
    public String securedPage(@AuthenticationPrincipal Principal principal)
    {
        if(principal != null) {

            System.out.print("principal");
            System.out.print(principal.getName());
            String name = principal.getName();

            return name;
        }
        else
        {
            System.out.print("nuts");
            return "";
        }

    }
	
	@RequestMapping(value = "/loggeduser123", produces =MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<String> findMessagesForUser(Principal principal) {
		System.out.println("logen in user");
	        return new ResponseEntity<String>(principal.getName(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/contacts")
	public Map<String, Object> contacts(HttpServletRequest req) {
		Map<String, Object> response = new HashMap<String, Object>();
		logger.info("contact start11111");
		System.out.println("username : "+req.getUserPrincipal().getName());
		try {
			response.put("contactList", contactsRepository.findByUsername(req.getUserPrincipal().getName()));
			response.put("status", "1");
			response.put("message", "success");
		} catch (Exception e) {
			response.put("status", "0");
			response.put("message", e.getMessage());
		}
		logger.info("contact end");
		return response;
	}

	@RequestMapping(value = "/directmessages")
	public Map<String, Object> directMessages(HttpServletRequest req) {
		Map<String, Object> response = new HashMap<String, Object>();
		logger.info("directmessages start");
		try {
			String username = req.getUserPrincipal().getName();
			response.put("contactList", contactsRepository.findByUsername(username));
			response.put("status", "1");
			response.put("message", "success");
		} catch (Exception e) {
			response.put("status", "0");
			response.put("message", e.getMessage());
		}
		logger.info("directmessages end");
		return response;
	}

	@RequestMapping(value = "/groupmessages")
	public Map<String, Object> groupMessages(HttpServletRequest req) {
		Map<String, Object> response = new HashMap<String, Object>();
		logger.info("groupmessages start");
		try {
			String username = req.getUserPrincipal().getName();
			response.put("contactList", contactsRepository.findByUsername(username));
			response.put("groupList", messagingDao.getGroupList(username));
			response.put("status", "1");
			response.put("message", "success");
		} catch (Exception e) {
			response.put("status", "0");
			response.put("message", e.getMessage());
		}
		logger.info("groupmessages end");
		return response;
	}

	@RequestMapping(value = "/addContacts")
	public Map<String, Object> addContacts(HttpServletRequest req,@Valid @RequestBody  Contacts contacts) {
		Map<String, Object> response = new HashMap<String, Object>();
		logger.info("addContacts start");
		try {
			logger.info("Name : "+contacts.getFullname()+" mobile - "+contacts.getMobile());
			String username = req.getUserPrincipal().getName();
			Contacts c = contactsRepository.findByMobileAndUsername(contacts.getMobile().trim(), username);
			if (c == null) {
				String currentTime = sdf.format(timestamp);
				contacts.setTimestamp(currentTime);
				contacts.setUsername(username);
				contactsRepository.save(contacts);
			}
			response.put("status", "1");
			response.put("message", "success");
		} catch (Exception e) {
			response.put("status", "0");
			response.put("message", e.getMessage());
		}
		logger.info("addContacts end");
		return response;
	}

	@RequestMapping(value = "/createGroup")
	public Map<String, Object> contacts(HttpServletRequest req, Groups groups) {
		Map<String, Object> response = new HashMap<String, Object>();
		logger.info("createGroup start");
		try {
			String username = req.getUserPrincipal().getName();
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
			response.put("contactList", contactsRepository.findByUsername(req.getUserPrincipal().getName()));
			response.put("status", "1");
			response.put("message", "success");
		} catch (Exception e) {
			response.put("status", "0");
			response.put("message", e.getMessage());
		}
		logger.info("createGroup end");
		return response;
	}

	@RequestMapping(value = "/countUnreadGroupMsg")
	public Map<String, Object> countUnreadGroupMsg(HttpServletRequest req, Model model) {
		Map<String, Object> response = new HashMap<String, Object>();
		logger.info("countUnreadGroupMsg start");
		try {
			String username = req.getUserPrincipal().getName();
			response.put("countUnreadGroupMsg", messagingDao.countUnreadGroupMsg(username));
			response.put("status", "1");
			response.put("message", "success");
		} catch (Exception e) {
			response.put("status", "0");
			response.put("message", e.getMessage());
		}
		logger.info("countUnreadGroupMsg end");
		return response;
	}

	@RequestMapping(value = "/groupMessageListener")
	public Map<String, Object> groupMessageListener(HttpServletRequest req, Model model) {
		Map<String, Object> response = new HashMap<String, Object>();
		logger.info("groupMessageListener start");
		try {
			String username = req.getUserPrincipal().getName();

			String groupId = req.getParameter("groupId");
			List<Map<String, Object>> getLatestGroupMessage = messagingDao.getLatestGroupMessage(groupId, "1",
					username);
			response.put("latestGroupMessage", getLatestGroupMessage);
			response.put("status", "1");
			response.put("message", "success");
		} catch (Exception e) {
			response.put("status", "0");
			response.put("message", e.getMessage());
		}
		logger.info("groupMessageListener end");
		return response;
	}

	@RequestMapping(value = "/getGroupMessageData")
	public Map<String, Object> getGroupMessageData(HttpServletRequest req, Model model) {
		Map<String, Object> response = new HashMap<String, Object>();
		logger.info("getGroupMessageData start");
		try {
			String username = req.getUserPrincipal().getName();
			String groupId = req.getParameter("groupId");
			response.put("groupMessageData", messagingDao.getGroupMessageData(groupId, "1", username));
			response.put("status", "1");
			response.put("message", "success");
		} catch (Exception e) {
			response.put("status", "0");
			response.put("message", e.getMessage());
		}
		logger.info("getGroupMessageData end");
		return response;
	}

	@RequestMapping(value = "/addGroupMessage")
	public Map<String, Object> addGroupMessage(HttpServletRequest req, Model model, GroupMessages groupMessages) {
		Map<String, Object> response = new HashMap<String, Object>();
		logger.info("addGroupMessage start");
		try {
			String username = req.getUserPrincipal().getName();
			String currentTime = sdf.format(timestamp);
			groupMessages.setTimestamp(currentTime);
			groupMessages.setFromUsername(username);
			groupMessages.setFlag("1");
			groupMessagesRepository.save(groupMessages);
			response.put("status", "1");
			response.put("message", "success");
		} catch (Exception e) {
			response.put("status", "0");
			response.put("message", e.getMessage());
		}
		logger.info("addGroupMessage end");
		return response;
	}

	@RequestMapping(value = "/getDirectMessagesData")
	public Map<String, Object> getDirectMessagesData(HttpServletRequest req, Model model) {
		Map<String, Object> response = new HashMap<String, Object>();
		logger.info("getDirectMessagesData start");
		try {
			String username = req.getUserPrincipal().getName();
			String tousername = req.getParameter("tousername");
			List<Map<String, Object>> directMessageList = messagingDao.getDirectMessages(username, tousername.trim());
			response.put("directMessageList", directMessageList);
			response.put("status", "1");
			response.put("message", "success");
		} catch (Exception e) {
			response.put("status", "0");
			response.put("message", e.getMessage());
		}
		logger.info("getDirectMessagesData end");
		return response;
	}

	@RequestMapping(value = "/directMessageListener")
	public Map<String, Object> directMessageListener(HttpServletRequest req, Model model) {
		Map<String, Object> response = new HashMap<String, Object>();
		logger.info("directMessageListener start");
		try {
			String username = req.getUserPrincipal().getName();
			String tousername = req.getParameter("tousername");
			List<Map<String, Object>> getLatestDirectMessageList = messagingDao.getLatestDirectMessage(username.trim(),
					tousername.trim());
			response.put("getLatestDirectMessageList", getLatestDirectMessageList);
			response.put("username", username);
			response.put("status", "1");
			response.put("message", "success");
		} catch (Exception e) {
			response.put("status", "0");
			response.put("message", e.getMessage());
		}
		logger.info("directMessageListener end");
		return response;
	}

	@RequestMapping(value = "/addDirectMessage")
	public Map<String, Object> addMessage(HttpServletRequest req, Messages messages, DirectMessages dirMessages,
			Model model) {
		Map<String, Object> response = new HashMap<String, Object>();
		logger.info("addDirectMessage start");
		try {
			String username = req.getUserPrincipal().getName();
			String currentTime = sdf.format(timestamp);
			DirectMessages directMessages = new DirectMessages();
			Messages msg = messagesRepository.findByToUsername(messages.getToMobile().trim());
			if (msg == null) {
				messages.setUsername(username);
				messages.setStatus("1");
				messages.setMessageCount("0");
				messages.setTimestamp(currentTime);
				messages = messagesRepository.save(messages);
				directMessages.setMessagesId(String.valueOf(messages.getId()));
			} else {
				directMessages.setMessagesId(String.valueOf(msg.getId()));
			}
			messagingDao.updateMessageCount(directMessages.getMessagesId(), username, messages.getToUsername().trim());
			directMessages.setFromUsername(username);
			directMessages.setToUsername(messages.getToUsername().trim());
			directMessages.setTextMessage(dirMessages.getTextMessage());
			directMessages.setTimestamp(currentTime);
			directMessages.setFlag("1");
			directMessagesRepository.save(directMessages);
			int status = 0;
			String toMobile = messages.getToMobile().trim();
			if (toMobile.equals(username))
				status = 0;
			else
				status = 1;

			response.put("status", status);
			response.put("message", "success");
		} catch (Exception e) {
			response.put("status", "0");
			response.put("message", e.getMessage());
		}
		logger.info("addDirectMessage end");
		return response;
	}

}
