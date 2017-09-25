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
import com.autmone.softmarket.service.ICommentService;
import com.autmone.softmarket.util.DateUtil;
import com.autmone.softmarket.util.ErrorHandle;
import com.autmone.softmarket.util.JsonUtils;
import com.autmone.softmarket.util.Util;
import com.autmone.softmarket.vo.Comment;

@Controller 
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private ICommentService commentService ;
	
	@RequestMapping(value="/addComment",method=RequestMethod.POST)
	public void addComment(HttpServletRequest request, HttpServletResponse response) {

		JSONObject paramsJson = Util.getInputJson(request);
		
		String type = paramsJson.getString("type");
		String typeId = paramsJson.getString("typeId");
		String commentContent = paramsJson.getString("commentContent");

		if(Util.isEmpty(commentContent) || Util.isEmpty(typeId)) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.FAIL, "请输入评论内容", null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		//获取登录用户
		Object userId = request.getAttribute("user_id");
		if(userId == null) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.NOT_LOGIN, ErrorHandle.NOT_LOGIN_STR, null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		Integer wxUserId = (Integer) userId;
		
		Comment comment = new Comment();
		comment.setUserId(wxUserId);
		comment.setType(Integer.parseInt(type));
		comment.setTypeId(Integer.parseInt(typeId));
		comment.setCommentContent(commentContent);
		comment.setAddTime(DateUtil.dateToString(new Date(), "YYYY-MM-dd HH:mm:ss"));
		commentService.insertComment(comment);
		
		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, "");
		Util.returnMsg(response, jsonStr);
		return ;
	}
	
	@RequestMapping(value="/getCommentList",method=RequestMethod.GET)
	public void getCommentListByPage(HttpServletRequest request, HttpServletResponse response) {

		String type = request.getParameter("type");
		String typeId = request.getParameter("typeId");
		String page = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		
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
		condition.put("type", type);
		condition.put("typeId", typeId);
		condition.put("startCount", (pageInt-1)*pageSizeInt);
		List<Map<String,Object>> list = commentService.selectCommentList(condition);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("list", list);
		
		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, returnMap);
		Util.returnMsg(response, jsonStr);
	}
}
