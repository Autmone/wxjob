package com.autmone.softmarket.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.autmone.softmarket.service.IUserService;
import com.autmone.softmarket.util.Constants;
import com.autmone.softmarket.util.DateUtil;
import com.autmone.softmarket.util.ErrorHandle;
import com.autmone.softmarket.util.HttpUtils;
import com.autmone.softmarket.util.JsonUtils;
import com.autmone.softmarket.util.MD5;
import com.autmone.softmarket.util.TokenUtil;
import com.autmone.softmarket.util.UrlConstants;
import com.autmone.softmarket.util.Util;
import com.autmone.softmarket.vo.User;
import com.autmone.softmarket.vo.WXUser;

@Controller
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	public void addUserInfo(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String userPassword = request.getParameter("password");

		if(Util.isEmpty(username) || Util.isEmpty(userPassword)) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.FAIL, "请输入用户名或密码", null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		if(username.length() < 6 || userPassword.length() < 6 || "cao".equals(username.toLowerCase()) || "tmd".equals(username.toLowerCase())) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.FAIL, "请输入符合要求的用户名或密码", null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		//查询用户名是否被注册
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("username", username);
		condition.put("startCount", 0);
		condition.put("pageSize", 1);
		List<User> userList = userService.getUserInfo(condition);
		if(userList != null && userList.size() > 0) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.FAIL, "该用户名已存在", null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		
		try {
			username = new String(username.getBytes("ISO-8859-1"),"UTF-8");
			userPassword = MD5.MD5Encode(userPassword);
			User user = new User(username,userPassword,new Date());
			userService.addUserInfo(user);
			System.out.println(user.getUserId());
			request.getSession().setAttribute("soft_user", user.getUserId());
			JSONObject json = new JSONObject();
			json.put("username", username);
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, json);
			Util.returnMsg(response, jsonStr);
			return ;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.FAIL, ErrorHandle.FAIL_STR, null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
	}

	@RequestMapping(value="/login",method=RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String userPassword = request.getParameter("password");
		
		if(Util.isEmpty(username) || Util.isEmpty(userPassword)) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.FAIL, "请输入用户名或密码", null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		if(username.length() < 6 || userPassword.length() < 6) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.FAIL, "请输入符合要求的用户名或密码", null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("username", username);
		userPassword = MD5.MD5Encode(userPassword);
		condition.put("userPassword", userPassword);
		condition.put("startCount", 0);
		condition.put("pageSize", 1);
		List<User> userList = userService.getUserInfo(condition);
		
		if(userList != null && userList.size() > 0) {
			System.out.println("==登录==" + userList.get(0).getUserId() + "==成功==");
			request.getSession().setAttribute("soft_user", userList.get(0).getUserId());
			JSONObject json = new JSONObject();
			json.put("username", username);
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, json);
			Util.returnMsg(response, jsonStr);
			return ;
		} else {
			System.out.println("==登录==" + username + "==失败==");
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.FAIL, "帐号或密码错误", null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
	}
	
	@RequestMapping(value="/wxLogin",method=RequestMethod.POST)
	public void wxLogin(HttpServletRequest request, HttpServletResponse response) {
		JSONObject paramsJson = Util.getInputJson(request);
		String code = paramsJson.getString("code");
		
		String httpUrl = UrlConstants.WEIXIN_USER_INFO.replace("APPID", Constants.APPID).replace("SECRET", Constants.APPSECRET).replace("JSCODE", code);
		String resultStr = HttpUtils.executeGet(httpUrl);
		
		JSONObject jsonObject = JSONObject.parseObject(resultStr);
		JSONObject json = new JSONObject();
		if(jsonObject.get("openid") != null) {
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("openid", jsonObject.get("openid"));
			WXUser wxUser = userService.getWXUserInfo(condition);
			if(wxUser == null) {
				//添加用户信息
				wxUser = new WXUser();
				wxUser.setOpenid(jsonObject.getString("openid"));
				wxUser.setSessionKey(jsonObject.getString("session_key"));
				wxUser.setAddTime(DateUtil.dateToString(new Date(), "YYYY-MM-dd HH:mm:ss"));
				userService.addWXUserInfo(wxUser);
				json.put("wx_info", 0);
			} else if(Util.isEmpty(wxUser.getWxNickname())) {
				json.put("wx_info", 0);
			} else {
				json.put("wx_info", 1);
			}
//			request.getSession().setAttribute("wx_user_id", wxUser.getWxUserId());
//			System.out.println(request.getSession().getAttribute("wx_user_id"));
			//生成token
			String token = TokenUtil.buildToken("1", wxUser.getWxUserId());
			json.put("token", token);
		}
		
		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, json);
		Util.returnMsg(response, jsonStr);
		return ;
	}
	
	@RequestMapping(value="/updateWXUser",method=RequestMethod.POST)
	public void updateWXUser(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("==updateWXUser");
		JSONObject paramsJson = Util.getInputJson(request);
		String wxNickName = paramsJson.getString("wxNickName");
		System.out.println(wxNickName);
//		try {
//			wxNickName = new String(wxNickName.getBytes("UTF-8"), "ISO-8859-1") ;
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		wxNickName = wxNickName.replace("🍄", "").replace("🍭", "");
//		System.out.println(wxNickName);
		
		String avatarUrl = paramsJson.getString("avatarUrl");
		System.out.println(avatarUrl);
		String gender = paramsJson.getString("gender");
		String province = paramsJson.getString("province");
		String city = paramsJson.getString("city");
		String country = paramsJson.getString("country");

		//获取登录用户
		Object userId = request.getAttribute("user_id");
		if(userId == null) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.NOT_LOGIN, ErrorHandle.NOT_LOGIN_STR, null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		Integer wxUserId = (Integer) userId;
		
		//获取登录用户
//		System.out.println(request.getSession().getAttribute("wx_user_id"));
//		String wxUserId = (String) request.getSession().getAttribute("wx_user_id");
		
		WXUser wxUser = new WXUser();
		wxUser.setWxUserId(wxUserId);
		wxUser.setWxNickname(wxNickName);
		wxUser.setAvatarUrl(avatarUrl);
		wxUser.setGender(gender);
		wxUser.setProvince(province);
		wxUser.setCity(city);
		wxUser.setCountry(country);
		
		userService.updateWXUser(wxUser);
		
		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, "");
		Util.returnMsg(response, jsonStr);
		return ;
	}
	

	@RequestMapping(value="/quit",method=RequestMethod.GET)
	public void quitUser(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();// 清空session

		Cookie[] cookies = ((HttpServletRequest) request).getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (Constants.SOFT_USERNAME.equals(cookie.getName())) {
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
					break;
				}
			}
		}
		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, null);
		Util.returnMsg(response, jsonStr);
		return ;
	}
}
