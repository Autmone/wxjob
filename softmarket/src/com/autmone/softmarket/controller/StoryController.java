package com.autmone.softmarket.controller;

import java.io.UnsupportedEncodingException;
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
import com.autmone.softmarket.service.IStoryService;
import com.autmone.softmarket.service.IUserService;
import com.autmone.softmarket.util.DateUtil;
import com.autmone.softmarket.util.ErrorHandle;
import com.autmone.softmarket.util.JsonUtils;
import com.autmone.softmarket.util.Util;
import com.autmone.softmarket.vo.Story;
import com.autmone.softmarket.vo.User;


@Controller 
@RequestMapping("/story")
public class StoryController {

	@Autowired
	private IStoryService storyService ;
	@Autowired
	private IUserService userService;
	
	/**
	 * 条件查询列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getStoryList",method=RequestMethod.GET)
	public void getStoryListByPage(HttpServletRequest request, HttpServletResponse response) {
		
		String storyTitle = request.getParameter("storyTitle");
		String storyType = request.getParameter("storyType");
		String page = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		
		if(!Util.isEmpty(storyTitle)) {
			try {
				storyTitle = new String(storyTitle.getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
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
		condition.put("storyTitle", storyTitle);
		condition.put("storyType", storyType);
		int count = storyService.getStoryListCount(condition);
		condition.put("startCount", ((pageInt-1)*pageSizeInt));
		List<Story> list = storyService.getStoryListByPage(condition);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("list", list);
		returnMap.put("totalCount", count);
		
		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, returnMap);
		Util.returnMsg(response, jsonStr);
	}
	
	@RequestMapping(value="/addStory",method=RequestMethod.POST)
	public void addStory(HttpServletRequest request, HttpServletResponse response) {
		
		String userId = String.valueOf(request.getSession().getAttribute("soft_user"));
		if(!"1314".equals(userId)) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.FAIL, ErrorHandle.FAIL_STR, null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		String storyTitle = request.getParameter("storyTitle");
		String storySummary = request.getParameter("storySummary");
		String storyContent = request.getParameter("storyContent");
		String storyAuthor = request.getParameter("storyAuthor");
		String storyType = request.getParameter("storyType");
		Date addTime = new Date();

		//强制转码
		if(!Util.isEmpty(storyTitle)) {
			try {
				storyTitle = new String(storyTitle.getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}if(!Util.isEmpty(storyContent)) {
			try {
				storyContent = new String(storyContent.getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		Story story = new Story(storyTitle,storySummary,storyContent,storyAuthor,Integer.parseInt(storyType),DateUtil.dateToString(addTime, "YYYY-MM-dd HH:mm:ss"));
		
		storyService.insertStory(story);

		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, null);
		Util.returnMsg(response, jsonStr);
	}

	@RequestMapping(value="/updateStory",method=RequestMethod.POST)
	public void updateStoryr(HttpServletRequest request, HttpServletResponse response) {
		
		String userId = String.valueOf(request.getSession().getAttribute("soft_user"));
		if(!"1314".equals(userId)) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.FAIL, ErrorHandle.FAIL_STR, null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		String storyTitle = request.getParameter("storyTitle");
		String storySummary = request.getParameter("storySummary");
		String storyContent = request.getParameter("storyContent");
		String storyAuthor = request.getParameter("storyAuthor");
		String storyType = request.getParameter("storyType");
		Date addTime = new Date();

		Story story = new Story(storyTitle,storySummary,storyContent,storyAuthor,Integer.parseInt(storyType),DateUtil.dateToString(addTime, "YYYY-mm-dd HH:mm:ss"));
		storyService.updateStory(story);

		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, null);
		Util.returnMsg(response, jsonStr);
	}
	
	/**
	 * 查询详情,如果有登录，判断当前登录用户是否赞助
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getStoryDetail",method=RequestMethod.GET)
	public void getStoryrDetail(HttpServletRequest request, HttpServletResponse response) {
		String userId = String.valueOf(request.getSession().getAttribute("soft_user"));
		String storyId = request.getParameter("storyId");

		Story story = new Story();
		story.setStoryId(Integer.parseInt(storyId));
		Story storyDetail = storyService.getStoryDetail(story);
		//更新点击次数
		storyService.updateStoryCheckNum(Integer.parseInt(storyId));
		
		//有登录的情况，先判断是否VIP
		if(!Util.isEmpty(userId)) {
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("userId", userId);
			condition.put("startCount", 0);
			condition.put("pageSize", 1);
			List<User> userList = userService.getUserInfo(condition);
			if(userList != null && userList.size() > 0) {
				User user = userList.get(0);
				if(user.getVipEndTime().after(new Date())) {	//是VIP，直接设置成不需要密码
					JSONObject json = new JSONObject();
					json.put("isVip", 0);
					json.put("storyContent", storyDetail.getStoryContent());
					String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, json);
					Util.returnMsg(response, jsonStr);
					return ;
				}
			}
		}
		//如果需要密码，不能在验证密码前返回URL信息
		if(storyDetail.getIsVip() == 1) {
			storyDetail.setStoryContent("");
		}
		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, storyDetail);
		Util.returnMsg(response, jsonStr);
		return ;
	}
	
}
