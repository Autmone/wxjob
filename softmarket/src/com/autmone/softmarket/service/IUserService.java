package com.autmone.softmarket.service;

import java.util.List;
import java.util.Map;

import com.autmone.softmarket.vo.User;
import com.autmone.softmarket.vo.WXUser;

public interface IUserService {

	/**
	 * 查询用户信息
	 */
	public List<User> getUserInfo(Map<String,Object> condition) ;
	
	/**
	 * 插入用户信息
	 * @param user
	 * @return
	 */
	public int addUserInfo(User user) ;
	
	/**
	 * 查询微信用户信息
	 */
	public WXUser getWXUserInfo(Map<String,Object> condition) ;
	
	/**
	 * 插入微信用户信息
	 * @param user
	 * @return
	 */
	public int addWXUserInfo(WXUser user) ;
	
	/**
	 * 更新微信用户信息
	 * @param user
	 * @return
	 */
	public int updateWXUser(WXUser user);
}
