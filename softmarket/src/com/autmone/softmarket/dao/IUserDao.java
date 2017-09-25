package com.autmone.softmarket.dao;

import java.util.List;
import java.util.Map;

import com.autmone.softmarket.vo.User;
import com.autmone.softmarket.vo.WXUser;

public interface IUserDao {

	/**
	 * 查询用户信息
	 */
	public List<User> selectUserInfo(Map<String,Object> condition) ;
	
	/**
	 * 插入用户信息
	 * @param user
	 * @return
	 */
	public int insertUserInfo(User user) ;
	
	/**
	 * 查询微信用户信息
	 */
	public WXUser selectWXUserInfo(Map<String,Object> condition) ;
	
	/**
	 * 插入微信用户信息
	 * @param user
	 * @return
	 */
	public int insertWXUserInfo(WXUser user) ;

	/**
	 * 更新微信用户信息
	 * @param user
	 * @return
	 */
	public int updateWXUser(WXUser user);
}
