package com.autmone.softmarket.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autmone.softmarket.dao.ITestDao;
import com.autmone.softmarket.service.ITestService;
import com.autmone.softmarket.vo.Test;

@Service
public class TestServiceImpl implements ITestService {

	@Autowired
	private ITestDao testDao ;
	
	@Override
	public List<Test> getTestInfo(Map<String, Object> condition) {
		return testDao.selectTestInfo(condition);
	}

	@Override
	public Test selectTestDetail(int testId) {
		return testDao.selectTestDetail(testId);
	}

}
