package com.autmone.softmarket.dao;

import java.util.List;
import java.util.Map;

import com.autmone.softmarket.vo.Test;

public interface ITestDao {

	/**
	 * 查询测试信息
	 */
	public List<Test> selectTestInfo(Map<String,Object> condition) ;
	
	/**
	 * 查询测试详情
	 * @param testId
	 * @return
	 */
	public Test selectTestDetail(int testId) ;
	
}
