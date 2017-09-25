package com.autmone.softmarket.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * fastjson 工具类
 * @author Administrator
 *
 */
public class JsonUtils {
	
	/**
	 * 将map转换为json字符串
	 * @param map
	 * @return
	 */
	public static String mapToJson(Object code,Object msg,Object obj){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", code);
		map.put("msg", msg);
		map.put("data", obj);
		Object json = com.alibaba.fastjson.JSONArray.toJSON(map); 
		//System.out.println(json.toString());// 
		return json.toString();
	}
	
	public static String mapToJson(Map<String,Object> map){
		Object json = com.alibaba.fastjson.JSONArray.toJSON(map); 
		return json.toString();
	}
	
	public static String mapToJson(String code,String msg,List list){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", code);
		map.put("msg", msg);
		map.put("data", list);
		Object json = com.alibaba.fastjson.JSONArray.toJSON(map); 
		//System.out.println(json.toString());// 
		return json.toString();
	}
	
}
