package com.hacker.slack.entity;
 
import javax.persistence.*;
 
@Entity 
@Table(name = "users")
public class Users{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int enabled;

	private String password;

	private String username;

	public Users() {
	}

	public Users(String mobile, String encodedPassword, int enabled) {
	 this.username = mobile;
	 this.password = encodedPassword;
	 this.enabled = enabled;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEnabled() {
		return this.enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}