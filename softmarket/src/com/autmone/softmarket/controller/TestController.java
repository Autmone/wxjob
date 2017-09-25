package com.autmone.softmarket.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autmone.softmarket.service.ITestService;
import com.autmone.softmarket.util.ErrorHandle;
import com.autmone.softmarket.util.JsonUtils;
import com.autmone.softmarket.util.Util;
import com.autmone.softmarket.vo.Test;

@Controller
@RequestMapping(value="/test")
public class TestController {

	@Autowired
	private ITestService testService;
	
	@RequestMapping(value="/getTestDetail",method=RequestMethod.GET)
	public void getTestDetail(HttpServletRequest request, HttpServletResponse response) {
		String testId = request.getParameter("testId");

		if(Util.isEmpty(testId)) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.FAIL, "参数错误", null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		
		Test test = testService.selectTestDetail(Integer.parseInt(testId));
		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, test);
		Util.returnMsg(response, jsonStr);
		return ;
	}

	@RequestMapping(value="/getTestList",method=RequestMethod.GET)
	public void getTestList(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("type", type);
		condition.put("state", 1);
		
		List<Test> testList = testService.getTestInfo(condition);
		JSONArray returnJson = new JSONArray();
		for(Test test : testList) {
			JSONObject json = new JSONObject();
			json.put("url", test.getTest_img_url());
			json.put("function", test.getType() == 0?"":(test.getType() == 1?"onClickTest1":"onClickTest2"));
			json.put("testId", test.getTest_id());
			returnJson.add(json);
		}
		
		
		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, returnJson);
		Util.returnMsg(response, jsonStr);
		return ;
	}

}
