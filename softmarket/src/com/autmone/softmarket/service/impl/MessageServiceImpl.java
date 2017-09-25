package com.autmone.softmarket.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autmone.softmarket.dao.IMessageDao;
import com.autmone.softmarket.service.IMessageService;
import com.autmone.softmarket.vo.Message;

@Service
public class MessageServiceImpl implements IMessageService {

	@Autowired
	private IMessageDao messageDao ;

	@Override
	public List<Map<String, Object>> getMsgListByPage(Map<String, Object> condition) {
		return messageDao.selectMsgListByPage(condition);
	}

	@Override
	public Integer insertMessage(Message message) {
		return messageDao.insertMessage(message);
	}

	@Override
	public List<Map<String,Object>> selectMsgDetail(Map<String,Object> condition) {
		return messageDao.selectMsgDetail(condition);
	}

	@Override
	public boolean updateMessageView(Map<String,Object> condition) {
		return messageDao.updateMessageView(condition);
	}

	@Override
	public boolean updateMessagePid(Map<String,Object> condition) {
		return messageDao.updateMessagePid(condition);
	}

	@Override
	public boolean delMessage(Map<String,Object> condition) {
		return messageDao.delMessage(condition);
	}
}
