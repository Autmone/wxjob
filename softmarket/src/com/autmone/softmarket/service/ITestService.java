package com.autmone.softmarket.service;

import java.util.List;
import java.util.Map;

import com.autmone.softmarket.vo.Test;

public interface ITestService {

	/**
	 * 查询测试信息
	 */
	public List<Test> getTestInfo(Map<String,Object> condition) ;
	
	/**
	 * 查询测试详情
	 * @param testId
	 * @return
	 */
	public Test selectTestDetail(int testId) ;
}
