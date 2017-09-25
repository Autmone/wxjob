package com.autmone.softmarket.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.autmone.softmarket.util.ErrorHandle;
import com.autmone.softmarket.util.JsonUtils;
import com.autmone.softmarket.util.Util;

@Controller
@RequestMapping(value="/menu")
public class MenuController {


	@RequestMapping(value="/getMenuList",method=RequestMethod.GET)
	public void getTestList(HttpServletRequest request, HttpServletResponse response) {
		
		JSONArray jsonArray = JSONArray.parseArray("[{'menuName':'段子','menuUrl':'/pages/info/info_index','open_type':'redirect'},"
				+ "{'menuName':'图片','menuUrl':'/pages/image/image_list', 'open_type': 'redirect'},"
				+ "{ 'menuName': '更多', 'menuUrl': '/pages/menu/menu', 'open_type': 'navigate' }]");
		
		
		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, jsonArray);
		Util.returnMsg(response, jsonStr);
		return ;
	}

}
