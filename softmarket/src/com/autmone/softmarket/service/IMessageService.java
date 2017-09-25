package com.autmone.softmarket.service;

import java.util.List;
import java.util.Map;

import com.autmone.softmarket.vo.Message;

public interface IMessageService {

	/**
	 * 列表查询
	 * @param condition
	 * @return
	 */
	public List<Map<String,Object>> getMsgListByPage(Map<String,Object> condition) ;
	
	/**
	 * 新增
	 * @param story
	 * @return
	 */
	public Integer insertMessage(Message message) ;
	
	/**
	 * 查询详情
	 * @param story
	 * @return
	 */
	public List<Map<String,Object>> selectMsgDetail(Map<String,Object> condition) ;
	
	/**
	 * 更新点击次数
	 * @param story
	 * @return
	 */
	public boolean updateMessageView(Map<String,Object> condition) ;
	
	public boolean updateMessagePid(Map<String,Object> condition) ;

	public boolean delMessage(Map<String,Object> condition) ;
}
