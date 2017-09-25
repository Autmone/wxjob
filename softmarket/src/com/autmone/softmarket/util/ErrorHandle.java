package com.autmone.softmarket.util;

public class ErrorHandle {
	public static final int SUCCESS = 0;// 请求成功
	public static final String SUCCESS_STR = "success";// 请求成功
	
	public static final int FAIL = 400001;// 请求失败
	public static final String FAIL_STR = "请求失败，请稍后再试";// 请求失败
	

	public static final int NOT_LOGIN = 400002;// 未登录
	public static final String NOT_LOGIN_STR = "未登录";// 未登录
}
