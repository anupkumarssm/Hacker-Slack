package com.hacker.slack.entity;
 
import javax.persistence.*;
 
@Entity
@Table(name="user_role")
public class UserRole{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String role;

	private String username;

	public UserRole() {
	}

	public UserRole(String username, String role) {
		this.username = username;
		this.role = role;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}