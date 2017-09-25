package com.autmone.softmarket.vo;

import java.util.Date;

public class User {

	private int userId;
	private String username;
	private String userPassword;
	private Date vipEndTime;
	private Date addTime;
	private Date updateTime;
	
	public User() {}
	public User(String username, String userPassword, Date addTime) {
		super();
		this.username = username;
		this.userPassword = userPassword;
		this.addTime = addTime;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public Date getVipEndTime() {
		return vipEndTime;
	}
	public void setVipEndTime(Date vipEndTime) {
		this.vipEndTime = vipEndTime;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
