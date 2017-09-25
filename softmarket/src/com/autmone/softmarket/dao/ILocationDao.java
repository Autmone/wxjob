package com.autmone.softmarket.dao;

import java.util.List;
import java.util.Map;

import com.autmone.softmarket.vo.Location;

public interface ILocationDao {

	/**
	 * 列表查询微小说列表
	 * @param condition
	 * @return
	 */
	public List<Map<String,Object>> selectUserListByLocal(Map<String,Object> condition) ;
	
	/**
	 * 新增微小说信息
	 * @param story
	 * @return
	 */
	public int insertLocation(Location location) ;
	
	/**
	 * 更新微小说信息
	 * @param story
	 * @return
	 */
	public boolean updateLocation(Location location) ;
	
	/**
	 * 查询微小说详情
	 * @param story
	 * @return
	 */
	public Location selectLocationDetail(int wxUserId) ;
	
	/**
	 * 更新点击次数
	 * @return
	 */
	public boolean updateLocationCheckNum(int locationId) ;
	
	
}
