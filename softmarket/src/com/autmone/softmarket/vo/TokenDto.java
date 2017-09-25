package com.autmone.softmarket.vo;

import java.util.Date;

/**
 * token的相关数据封装类
 *
 * @author zhuwenjun 2014年6月3日
 *
 */
public class TokenDto {

	/** 用户类型 */
	private String userType;

	/** 用户id */
	private int userId = -1;

	/** 时间戳 */
	private Date time;

	/** token解析结果 */
	private int result = -1;
	
	/** 商户ID */
	private int partnerId = -1;

	/**
	 * 设定用户类型
	 * 
	 * @param argUserType
	 *            用户类型
	 */
	public void setUserType(String argUserType) {
		this.userType = argUserType;
	}

	/**
	 * 获取用户类型
	 * 
	 * @return 用户类型
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * 设定用户id
	 * 
	 * @param argUserId
	 *            用户id
	 */
	public void setUserId(int argUserId) {
		this.userId = argUserId;
	}

	/**
	 * 获取用户id
	 * 
	 * @return 用户id
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * 设定时间戳
	 * 
	 * @param argTime
	 *            时间戳
	 */
	public void setTime(Date argTime) {
		this.time = argTime;
	}

	/**
	 * 获取时间戳
	 * 
	 * @return 时间戳
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * token解析结果
	 * 
	 * @param result
	 *            token解析结果
	 */
	public void setResult(int result) {
		this.result = result;
	}

	/**
	 * 获取token解析结果
	 * 
	 * @return token解析结果
	 */
	public int getResult() {
		return result;
	}

	public int getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}


}
