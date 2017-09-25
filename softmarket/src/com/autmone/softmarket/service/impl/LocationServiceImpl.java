package com.autmone.softmarket.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autmone.softmarket.dao.ILocationDao;
import com.autmone.softmarket.service.ILocationService;
import com.autmone.softmarket.vo.Location;

@Service
public class LocationServiceImpl implements ILocationService {

	@Autowired
	private ILocationDao locationDao ;

	@Override
	public List<Map<String, Object>> getUserListByLocal(Map<String, Object> condition) {
		return locationDao.selectUserListByLocal(condition);
	}

	@Override
	public int insertLocation(Location location) {
		return locationDao.insertLocation(location);
	}

	@Override
	public boolean updateLocation(Location location) {
		return locationDao.updateLocation(location);
	}

	@Override
	public Location selectLocationDetail(int wxUserId) {
		return locationDao.selectLocationDetail(wxUserId);
	}

	@Override
	public boolean updateLocationCheckNum(int locationId) {
		return locationDao.updateLocationCheckNum(locationId);
	}


}
