package com.autmone.softmarket.service;

import java.util.List;
import java.util.Map;

import com.autmone.softmarket.vo.Location;

public interface ILocationService {

	/**
	 * 列表查询
	 * @param condition
	 * @return
	 */
	public List<Map<String,Object>> getUserListByLocal(Map<String,Object> condition) ;
	
	/**
	 * 新增
	 * @param story
	 * @return
	 */
	public int insertLocation(Location location) ;
	
	/**
	 * 更新
	 * @param story
	 * @return
	 */
	public boolean updateLocation(Location location) ;
	
	/**
	 * 查询详情
	 * @param story
	 * @return
	 */
	public Location selectLocationDetail(int wxUserId) ;
	
	/**
	 * 更新点击次数
	 * @param story
	 * @return
	 */
	public boolean updateLocationCheckNum(int locationId) ;
	
}
