package com.autmone.softmarket.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autmone.softmarket.dao.InformationDao;
import com.autmone.softmarket.service.InformationService;
import com.autmone.softmarket.vo.Information;

@Service
public class InformationServiceImpl implements InformationService {

	@Resource(name = "informationDao")
	private InformationDao informationDao ;

	@Override
	public List<Information> getInfoListByPage(Map<String, Object> condition) {
		return informationDao.selectInfoListByPage(condition);
	}

	@Override
	public int getInfoListCount(Map<String, Object> condition) {
		return informationDao.selectInfoListCount(condition);
	}

	@Override
	public int insertInformation(Information information) {
		return informationDao.insertInformation(information);
	}

	@Override
	public boolean updateInformation(Information information) {
		return informationDao.updateInformation(information);
	}

	@Override
	public boolean delInfomation(Information informtion) {
		return informationDao.delInfomation(informtion);
	}
	
	public Information getInfoDetail(Information information) {
		return informationDao.selectInfoDetail(information);
	}
	
	public boolean updateInfoCheckNum(int infoId) {
		return informationDao.updateInfoCheckNum(infoId);
	}

	public boolean updateInfoDownNum(int infoId) {
		return informationDao.updateInfoDownNum(infoId);
	}
}
