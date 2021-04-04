package com.hacker.slack.dao;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessagingDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
  
	public void updateTime(String username, String time) {
		try {
			String query = "update messages m set m.timestamp=? where m.to_username=?";
			jdbcTemplate.update(query, time, username);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Map<String, Object>> getGroupList(String username) {
		try {
			String query = "select grp.*,c.fullname from (SELECT g.group_name,g.timestamp,g.id,g.created_by FROM groups_tb g,group_users gu where g.id=gu.group_id and gu.mobile=?) as grp join contacts c on c.mobile=grp.created_by group by id";
			return jdbcTemplate.queryForList(query, username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Map<String, Object>> getGroupMessageData(String groupId, String flag,String username) {
		try {
			String query = "SELECT gm.*,up.fullname FROM group_messages gm left join user_profile up on up.username=gm.from_username where gm.group_id=?";
			List<Map<String, Object>>  resultGroupMessageList =  jdbcTemplate.queryForList(query, groupId);
			
			String sqlqry = "SELECT * FROM group_messages where group_id=? and flag=? and from_username !=?";
			List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sqlqry, groupId, flag,username);
			 for(int i=0;i<resultList.size();i++) {
				String updateQuery = "update group_messages set flag=0 where id=? and flag=1";
				jdbcTemplate.update(updateQuery, resultList.get(i).get("id").toString());
			}
			 return resultGroupMessageList;
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Map<String, Object>> getLatestGroupMessage(String groupId, String flag,String username) {
		try {
				String query = "SELECT gm.*,up.fullname FROM group_messages gm left join user_profile up on up.username=gm.from_username where gm.group_id=? and gm.flag=? and gm.from_username !=?";
				List<Map<String, Object>> resultList = jdbcTemplate.queryForList(query, groupId, flag,username);
				 for(int i=0;i<resultList.size();i++) {
					String updateQuery = "update group_messages set flag=0 where id=? and flag=1";
					jdbcTemplate.update(updateQuery, resultList.get(i).get("id").toString());
				}
				return resultList;
		} catch (Exception e) {
		}
		return null;
	}
   
	public List<Map<String, Object>> countUnreadGroupMsg(String username) {
		try {
			String query = "SELECT count(gm.flag) as count,gm.group_id FROM group_users gu,group_messages gm where gu.mobile=? and gm.from_username != ? and gu.group_id=gm.group_id and gm.flag=1 group by gm.group_id";
			return jdbcTemplate.queryForList(query,username, username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
 
	 
}
