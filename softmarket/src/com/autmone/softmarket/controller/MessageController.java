package com.autmone.softmarket.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.autmone.softmarket.service.IMessageService;
import com.autmone.softmarket.util.DateUtil;
import com.autmone.softmarket.util.ErrorHandle;
import com.autmone.softmarket.util.JsonUtils;
import com.autmone.softmarket.util.Util;
import com.autmone.softmarket.vo.Message;


@Controller 
@RequestMapping("/message")
public class MessageController {

	@Autowired
	private IMessageService messageService ;
	
	/**
	 * 条件查询列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getMsgListByPage",method=RequestMethod.GET)
	public void getMsgListByPage(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("==getMsgListByPage==");

		String page = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		
		try {
		//获取登录用户
		Object userId = request.getAttribute("user_id");
		if(userId == null) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.NOT_LOGIN, ErrorHandle.NOT_LOGIN_STR, null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		Integer wxUserId = (Integer) userId;
		
		if(Util.isEmpty(page)) {
			page = "1";
		}
		if(Util.isEmpty(pageSize)) {
			pageSize = "10";
		}
		
		Map<String,Object> condition = new HashMap<String,Object>();
		int pageInt = Integer.parseInt(page);
		int pageSizeInt = Integer.parseInt(pageSize);
		condition.put("pageSize", pageSizeInt);
		condition.put("startCount", (pageInt-1)*pageSizeInt);
		condition.put("fromUserId", wxUserId);
		condition.put("toUserId", wxUserId);
		List<Map<String, Object>> msgList = messageService.getMsgListByPage(condition);
		
		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, msgList);
		Util.returnMsg(response, jsonStr);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询详情
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getMessagerDetail",method=RequestMethod.GET)
	public void getMessagerDetail(HttpServletRequest request, HttpServletResponse response) {
		String msgPid = request.getParameter("msgPid");

		String page = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		
		try {
			//获取登录用户
			Object userId = request.getAttribute("user_id");
			if(userId == null) {
				String jsonStr = JsonUtils.mapToJson(ErrorHandle.NOT_LOGIN, ErrorHandle.NOT_LOGIN_STR, null);
				Util.returnMsg(response, jsonStr);
				return ;
			}
			Integer wxUserId = (Integer) userId;
			
			if(Util.isEmpty(page)) {
				page = "1";
			}
			if(Util.isEmpty(pageSize)) {
				pageSize = "10";
			}
			
			Map<String,Object> condition = new HashMap<String,Object>();
			int pageInt = Integer.parseInt(page);
			int pageSizeInt = Integer.parseInt(pageSize);
			condition.put("pageSize", pageSizeInt);
			condition.put("startCount", (pageInt-1)*pageSizeInt);
			condition.put("msgPid", msgPid);
			condition.put("fromUserId", wxUserId);
			condition.put("toUserId", wxUserId);
			List<Map<String,Object>> msgDetailList = messageService.selectMsgDetail(condition);
			
			String toUserId = "";
			for(Map<String,Object> map : msgDetailList) {
				if(userId.toString().equals(map.get("fromUserId").toString())) {
					map.put("fromWxNickname", "我");
					if(Util.isEmpty(toUserId)) {
						toUserId = map.get("toUserId").toString();	
					}
				} else {
					if(Util.isEmpty(toUserId)) 
						toUserId = map.get("fromUserId").toString();
				}
			}
			
			Map<String,Object> returnMap = new HashMap<String,Object>();
			returnMap.put("list", msgDetailList);
			returnMap.put("toUserId", toUserId);
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, returnMap);
			Util.returnMsg(response, jsonStr);
			return ;
		} catch(Exception e ) {
			e.printStackTrace();
		}
	}
	

	@RequestMapping(value="/pushMessage",method=RequestMethod.POST)
	public void pushMessage(HttpServletRequest request, HttpServletResponse response) {

		JSONObject paramsJson = Util.getInputJson(request);
		
		if(paramsJson == null) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.FAIL, "参数不符合要求", null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		
		String toUserId = paramsJson.getString("toUserId");
		String content = paramsJson.getString("content");
		String msgPid = paramsJson.getString("msgPid");
		
		//获取登录用户
		Object userId = request.getAttribute("user_id");
		if(userId == null) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.NOT_LOGIN, ErrorHandle.NOT_LOGIN_STR, null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		Integer wxUserId = (Integer) userId;
		
		if(Util.isEmpty(content)) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.FAIL, "请输入内容", null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		
		if(Util.isEmpty(msgPid)) {
			msgPid = "0";
		}
		
		Message message = new Message(content, wxUserId.intValue(), Integer.parseInt(toUserId), 0, Integer.parseInt(msgPid),
				1, DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		
		messageService.insertMessage(message);
		
		if("0".equals(msgPid)) {
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("msgPid", message.getMsgId());
			condition.put("msgId", message.getMsgId());
			messageService.updateMessagePid(condition);
		}

		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, "");
		Util.returnMsg(response, jsonStr);
		return ;
	}

	@RequestMapping(value="/delMessage",method=RequestMethod.POST)
	public void delMessage(HttpServletRequest request, HttpServletResponse response) {
		JSONObject paramsJson = Util.getInputJson(request);
		
		if(paramsJson == null) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.FAIL, "参数不符合要求", null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		
		String msgPid = paramsJson.getString("msgPid");
		
		//获取登录用户
		Object userId = request.getAttribute("user_id");
		if(userId == null) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.NOT_LOGIN, ErrorHandle.NOT_LOGIN_STR, null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		Integer wxUserId = (Integer) userId;
		
		if(!Util.isNumeric(msgPid)) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.FAIL, "参数不符合要求", null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("msgPid", msgPid);
		condition.put("userId", wxUserId);
		messageService.delMessage(condition);

		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, "");
		Util.returnMsg(response, jsonStr);
		return ;
		
	}
}
