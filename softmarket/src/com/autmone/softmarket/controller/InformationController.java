package com.autmone.softmarket.controller;

import java.io.IOException;
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
import com.autmone.softmarket.service.IUserService;
import com.autmone.softmarket.service.InformationService;
import com.autmone.softmarket.util.ErrorHandle;
import com.autmone.softmarket.util.JsonUtils;
import com.autmone.softmarket.util.Util;
import com.autmone.softmarket.vo.Information;
import com.autmone.softmarket.vo.User;
import com.autmone.softmarket.vo.WXMessage;


@Controller 
@RequestMapping("/information")
public class InformationController {

	@Autowired
	private InformationService informationService ;
	@Autowired
	private IUserService userService;
	
	/**
	 * 条件查询列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getInfoList",method=RequestMethod.GET)
	public void getInfoListByPage(HttpServletRequest request, HttpServletResponse response) {
		
		String infoTitle = request.getParameter("infoTitle");
		String infoType = request.getParameter("infoType");
		String page = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		
		if(!Util.isEmpty(infoTitle)) {
			try {
				infoTitle = new String(infoTitle.getBytes("ISO-8859-1"),"UTF-8");
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
		condition.put("infoTitle", infoTitle);
		condition.put("infoType", infoType);
		int count = informationService.getInfoListCount(condition);
		condition.put("startCount", (pageInt-1)*pageSizeInt);
		List<Information> list = informationService.getInfoListByPage(condition);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("list", list);
		returnMap.put("totalCount", count);
		
		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, returnMap);
		Util.returnMsg(response, jsonStr);
	}
	
	@RequestMapping(value="/addInfor",method=RequestMethod.POST)
	public void addInformation(HttpServletRequest request, HttpServletResponse response) {
		
		String userId = String.valueOf(request.getSession().getAttribute("soft_user"));
		if(!"1314".equals(userId)) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.FAIL, ErrorHandle.FAIL_STR, null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		String infoTitle = request.getParameter("infoTitle");
		String infoContent = request.getParameter("infoContent");
		String infoUrl = request.getParameter(" ");
		String isPassword = request.getParameter("isPassword");
		String infoPassword = request.getParameter("infoPassword");
		String isFree = request.getParameter("isFree");
		String infoType = request.getParameter("infoType");
		String infoNum = request.getParameter("infoNum");

		//强制转码
		if(!Util.isEmpty(infoTitle)) {
			try {
				infoTitle = new String(infoTitle.getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}if(!Util.isEmpty(infoContent)) {
			try {
				infoContent = new String(infoContent.getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		Information information = new Information(infoTitle,infoContent,infoUrl,Integer.parseInt(isPassword),Integer.parseInt(isFree),new Date(),Integer.parseInt(infoType),infoNum,infoPassword);
		
		informationService.insertInformation(information);

		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, null);
		Util.returnMsg(response, jsonStr);
	}

	@RequestMapping(value="/updateInfor",method=RequestMethod.POST)
	public void updateInfor(HttpServletRequest request, HttpServletResponse response) {
		
		String userId = String.valueOf(request.getSession().getAttribute("soft_user"));
		if(!"1314".equals(userId)) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.FAIL, ErrorHandle.FAIL_STR, null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		String infoTitle = request.getParameter("infoTitle");
		String infoContent = request.getParameter("infoContent");
		String infoUrl = request.getParameter("infoUrl");
		String isPassword = request.getParameter("isPassword");
		String infoPassword = request.getParameter("infoPassword");
		String isFree = request.getParameter("isFree");
		String infoType = request.getParameter("infoType");
		String infoNum = request.getParameter("infoNum");
		
		Information information = new Information(infoTitle,infoContent,infoUrl,Integer.parseInt(isPassword),Integer.parseInt(isFree),new Date(),Integer.parseInt(infoType),infoNum,infoPassword);
		information.setUpdateTime(new Date());
		informationService.updateInformation(information);

		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, null);
		Util.returnMsg(response, jsonStr);
	}
	
	/**
	 * 查询详情,如果有登录，判断当前登录用户是否赞助
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getInforDetail",method=RequestMethod.GET)
	public void getInforDetail(HttpServletRequest request, HttpServletResponse response) {
		String userId = String.valueOf(request.getSession().getAttribute("soft_user"));
		String infoId = request.getParameter("infoId");

		Information information = new Information();
		information.setInfoId(Integer.parseInt(infoId));
		Information info = informationService.getInfoDetail(information);
		//更新点击次数
		informationService.updateInfoCheckNum(Integer.parseInt(infoId));
		
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
					json.put("isPassword", 0);
					json.put("infoUrl", info.getInfoUrl());
					json.put("infoTitle", info.getInfoTitle());
					String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, json);
					Util.returnMsg(response, jsonStr);
					return ;
				}
			}
		}
		//如果需要密码，不能在验证密码前返回URL信息
		if(info.getIsPassword() == 1) {
			info.setInfoUrl("");
		}
		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, info);
		Util.returnMsg(response, jsonStr);
		return ;
	}
	
	/**
	 * 校验资料密码
	 */
	@RequestMapping(value="/checkPassword",method=RequestMethod.GET)
	public void checkPassword(HttpServletRequest request, HttpServletResponse response) {
		String infoPassword = request.getParameter("infoPassword");
		String infoId = request.getParameter("infoId");

		if(Util.isEmpty(infoPassword)) {
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.FAIL, ErrorHandle.FAIL_STR, null);
			Util.returnMsg(response, jsonStr);
			return ;
		}
		
		Information information = new Information();
		information.setInfoId(Integer.parseInt(infoId));
		information.setInfoPassword(infoPassword);
		Information info = informationService.getInfoDetail(information);
		
		String jsonStr = "";
		if(info != null) {
			//更新下载次数
			informationService.updateInfoCheckNum(Integer.parseInt(infoId));
			jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, info);
		} else {
			jsonStr = JsonUtils.mapToJson(ErrorHandle.FAIL, ErrorHandle.FAIL_STR, null);
		}
		Util.returnMsg(response, jsonStr);
	}

	@RequestMapping(value="/message",method=RequestMethod.POST)
	public void message(HttpServletRequest request, HttpServletResponse response) {

		String xmlStr = "";
		try {
			xmlStr = Util.inputStreamToString(request.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("=====" + xmlStr);
		
		WXMessage wxMessage = (WXMessage)Util.getObjectFromXML(xmlStr, WXMessage.class);
		
		WXMessage returnWXMessage = new WXMessage();
		returnWXMessage.setToUserName(wxMessage.getFromUserName());
		returnWXMessage.setFromUserName(wxMessage.getToUserName());
		returnWXMessage.setMsgType("text");
		
		if("voice".equals(wxMessage.getMsgType())) {
			String recognition = wxMessage.getRecognition();
			if(recognition.indexOf("加") != -1) {
				String regex = "加";
				String[] datas = recognition.split(regex);
				
				String numb1 = "";
				for(int index=0 ; index < datas[0].length() ; index++) {
					numb1 += stringToInt(datas[0].substring(index, index+1));
				}
				String numb2 = "";
				for(int index=0 ; index < datas[1].length() ; index++) {
					numb2 += stringToInt(datas[1].substring(index, index+1));
				}
				
				String result = Integer.parseInt(numb1) + Integer.parseInt(numb2) + "";
				returnWXMessage.setContent(result);
				returnWXMessage.setCreateTime(new Date().getTime());
			} else if(recognition.indexOf("减") != -1) {
				String regex = "减";
				String[] datas = recognition.split(regex);

				String numb1 = "";
				for(int index=0 ; index < datas[0].length() ; index++) {
					numb1 += stringToInt(datas[0].substring(index, index+1));
				}
				String numb2 = "";
				for(int index=0 ; index < datas[1].length() ; index++) {
					numb2 += stringToInt(datas[1].substring(index, index+1));
				}
				
				String result = Integer.parseInt(numb1) - Integer.parseInt(numb2) + "";
				returnWXMessage.setContent(result);
				returnWXMessage.setCreateTime(new Date().getTime());
				
			} else if(recognition.indexOf("乘") != -1) {
				String regex = "乘";
				String[] datas = recognition.split(regex);

				String numb1 = "";
				for(int index=0 ; index < datas[0].length() ; index++) {
					numb1 += stringToInt(datas[0].substring(index, index+1));
				}
				String numb2 = "";
				for(int index=0 ; index < datas[1].length() ; index++) {
					numb2 += stringToInt(datas[1].substring(index, index+1));
				}
				
				String result = Integer.parseInt(numb1) * Integer.parseInt(numb2) + "";
				returnWXMessage.setContent(result);
				returnWXMessage.setCreateTime(new Date().getTime());
				
			} else if(recognition.indexOf("除") != -1) {
				String regex = "除";
				String[] datas = recognition.split(regex);

				String numb1 = "";
				for(int index=0 ; index < datas[0].length() ; index++) {
					numb1 += stringToInt(datas[0].substring(index, index+1));
				}
				String numb2 = "";
				for(int index=0 ; index < datas[1].length() ; index++) {
					numb2 += stringToInt(datas[1].substring(index, index+1));
				}
				
				String result = Integer.parseInt(numb1) / Integer.parseInt(numb2) + "";
				returnWXMessage.setContent(result);
				returnWXMessage.setCreateTime(new Date().getTime());
				
			} else {
				returnWXMessage.setContent("还没听明白你在说什么");
				returnWXMessage.setCreateTime(new Date().getTime());
			}
		} else {
			returnWXMessage.setContent("还没听明白你在说什么");
			returnWXMessage.setCreateTime(new Date().getTime());
		}

		System.out.println(Util.getXmlFromObject(returnWXMessage));
		Util.returnMsg(response, Util.getXmlFromObject(returnWXMessage));
	}
	
	public String stringToInt(String str) {
		if("零".equals(str)) {
			return "0";
		}
		if("一".equals(str)) {
			return "1";
		}
		if("二".equals(str)) {
			return "2";
		}
		if("三".equals(str)) {
			return "3";
		}
		if("四".equals(str)) {
			return "4";
		}
		if("五".equals(str)) {
			return "5";
		}
		if("六".equals(str)) {
			return "6";
		}
		if("七".equals(str)) {
			return "7";
		}
		if("八".equals(str)) {
			return "8";
		}
		if("九".equals(str)) {
			return "9";
		}
		if("点".equals(str)) {
			return ".";
		}
		return "";
	}
	
}
