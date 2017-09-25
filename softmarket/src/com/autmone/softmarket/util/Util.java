package com.autmone.softmarket.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;

/**
 */
public class Util {

	/**
	 * 将json结果集用流的形式返回
	 * 
	 * @param response
	 * @param jsonString
	 */
	public static void returnMsg(HttpServletResponse response, String jsonString) {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(jsonString);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
	
	/**
	 * 判断字符串是否为空
	 * @param string
	 * @return	false：不为空，true：为空
	 */
	public static boolean isEmpty(String string) {
		if(string != null && !string.isEmpty() && !"null".equals(string)) {
			return false;
		}
		return true;
	}

	// 判断是否为正整数
	public static boolean isNumeric(String str) {
		if (isEmpty(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	public static Object getObjectFromXML(String xml, Class tClass) {
    	
        //将从API返回的XML数据映射到Java对象
        XStream xStreamForResponseData = new XStream();
        xStreamForResponseData.alias("xml", tClass);
        xStreamForResponseData.ignoreUnknownElements();//暂时忽略掉一些新增的字段
        return xStreamForResponseData.fromXML(xml);
    	
    }
	
	public static JSONObject getInputJson(HttpServletRequest request) {
		JSONObject paramsJson = new JSONObject();
		String str = "";
		try {
			request.setCharacterEncoding("UTF-8");
			ServletInputStream inputStream = request.getInputStream();
			// inputString = filter(inputString);
			str = inputStreamToString(inputStream);
			paramsJson = JSONObject.parseObject(str);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return paramsJson;
	}
	
	public static String inputStreamToString(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString("UTF-8");
    }
	
	public static String getXmlFromObject(Object object){    //   <![CDATA[      ]]>
		XStream xStreamForResponseData = new XStream();
        xStreamForResponseData.alias("xml", object.getClass());
		return xStreamForResponseData.toXML(object);
    }

	public static String strFromGet(String str){
		
		String s = "";
		if(!Util.isEmpty(str)){
			try {
				s = URLDecoder.decode(str, "UTF-8");
			} catch (IllegalArgumentException e1) {
				//不做处理
				s = str;
			} catch (Exception e1) {
			}
		}
		
		return  s;
	}
}
