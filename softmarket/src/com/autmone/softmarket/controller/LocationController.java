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

import com.autmone.softmarket.service.ILocationService;
import com.autmone.softmarket.util.DateUtil;
import com.autmone.softmarket.util.ErrorHandle;
import com.autmone.softmarket.util.JsonUtils;
import com.autmone.softmarket.util.Util;
import com.autmone.softmarket.vo.Location;


@Controller 
@RequestMapping("/location")
public class LocationController {

	@Autowired
	private ILocationService locationService ;
	
	/**
	 * 条件查询列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getLocationList",method=RequestMethod.GET)
	public void getStoryListByPage(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("==getStoryListByPage==");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		String accuracy = request.getParameter("accuracy");
		String altitude = request.getParameter("altitude");
		String verticalAccuracy = request.getParameter("verticalAccuracy");
		String horizontalAccuracy = request.getParameter("horizontalAccuracy");

		String page = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		

		System.out.println(latitude + "==" + longitude + "==" + accuracy + "==" + altitude + "==" + verticalAccuracy + "==");
		
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
			pageSize = "20";
		}
		
		Location location = locationService.selectLocationDetail(wxUserId);
		if(location == null) {
			location = new Location(Double.valueOf(latitude), Double.valueOf(longitude), Integer.parseInt(accuracy)
					, Integer.parseInt(altitude), Integer.parseInt(verticalAccuracy),Integer.parseInt(horizontalAccuracy), wxUserId, 
					DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			locationService.insertLocation(location);
			
		} else if(location.getLatitude() != Double.valueOf(latitude) || location.getLongitude() != Double.valueOf(longitude)) {
			int locationId = location.getLocationId();
			location = new Location(Double.valueOf(latitude), Double.valueOf(longitude), Double.valueOf(accuracy)
					, Double.valueOf(altitude), Double.valueOf(verticalAccuracy),Double.valueOf(horizontalAccuracy), wxUserId, 
					DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			location.setUpdateTime(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			location.setLocationId(locationId);
			locationService.updateLocation(location);
		}
		
		Map<String,Object> condition = new HashMap<String,Object>();
		int pageInt = Integer.parseInt(page);
		int pageSizeInt = Integer.parseInt(pageSize);
		condition.put("pageSize", pageSizeInt);
		condition.put("startCount", (pageInt-1)*pageSizeInt);
		condition.put("latitudeMin", Double.valueOf(latitude) - 2);
		condition.put("latitudeMax", Double.valueOf(latitude) + 2);
		condition.put("longitudeMin", Double.valueOf(longitude) - 2);
		condition.put("longitudeMax", Double.valueOf(longitude) + 2);
		List<Map<String, Object>> localUser = locationService.getUserListByLocal(condition);
		
		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, localUser);
		Util.returnMsg(response, jsonStr);
		} catch(Exception e) {
			e.printStackTrace();
		}
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

		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, "");
		Util.returnMsg(response, jsonStr);
		return ;
	}
	
}
