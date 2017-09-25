package com.autmone.softmarket.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autmone.softmarket.dao.IUserDao;
import com.autmone.softmarket.service.IUserService;
import com.autmone.softmarket.vo.User;
import com.autmone.softmarket.vo.WXUser;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao ;
	
	@Override
	public List<User> getUserInfo(Map<String, Object> condition) {
		return userDao.selectUserInfo(condition);
	}

	@Override
	public int addUserInfo(User user) {
		return userDao.insertUserInfo(user);
	}

	@Override
	public WXUser getWXUserInfo(Map<String, Object> condition) {
		return userDao.selectWXUserInfo(condition);
	}

	@Override
	public int addWXUserInfo(WXUser user) {
		return userDao.insertWXUserInfo(user);
	}

	@Override
	public int updateWXUser(WXUser user) {
		return userDao.updateWXUser(user);
	}
}
