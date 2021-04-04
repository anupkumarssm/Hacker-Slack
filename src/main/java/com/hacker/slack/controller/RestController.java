package com.hacker.slack.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hacker.slack.dao.MessagingDao;
import com.hacker.slack.entity.GroupMessages;
import com.hacker.slack.repository.GroupMessagesRepository;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	MessagingDao messagingDao;

	@Autowired
	GroupMessagesRepository groupMessagesRepository;

	@RequestMapping(value = "/countUnreadGroupMsg")
	public List<Map<String, Object>> countUnreadGroupMsg(HttpServletRequest req, Model model) {
		String username = req.getUserPrincipal().getName();
		return messagingDao.countUnreadGroupMsg(username);
	}

	@RequestMapping(value = "/groupMessageListener")
	public List<Map<String, Object>> groupMessageListener(HttpServletRequest req, Model model) {
		String username = req.getUserPrincipal().getName();
		String groupId = req.getParameter("groupId");
		List<Map<String, Object>> getLatestGroupMessage = messagingDao.getLatestGroupMessage(groupId, "1", username);
		return getLatestGroupMessage;
	}

	@RequestMapping(value = "/getGroupMessageData")
	public List<Map<String, Object>> getGroupMessageData(HttpServletRequest req, Model model) {
		String username = req.getUserPrincipal().getName();
		String groupId = req.getParameter("groupId");
		return messagingDao.getGroupMessageData(groupId, "1", username);
	}

	@RequestMapping(value = "/addGroupMessage")
	public int addGroupMessage(HttpServletRequest req, Model model, GroupMessages groupMessages) {
		String username = req.getUserPrincipal().getName();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String currentTime = sdf.format(timestamp);
		groupMessages.setTimestamp(currentTime);
		groupMessages.setFromUsername(username);
		groupMessages.setFlag("1");
		groupMessagesRepository.save(groupMessages);
		return 1;
	}
}
