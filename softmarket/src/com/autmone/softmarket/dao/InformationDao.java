package com.autmone.softmarket.dao;

import java.util.List;
import java.util.Map;

import com.autmone.softmarket.vo.Information;

public interface InformationDao {

	/**
	 * 列表查询资料软件列表
	 * @param condition
	 * @return
	 */
	public List<Information> selectInfoListByPage(Map<String,Object> condition) ;
	
	/**
	 * 查询资料软件列表总条数
	 * @param condition
	 * @return
	 */
	public int selectInfoListCount(Map<String,Object> condition) ;
	
	/**
	 * 新增资料信息
	 * @param information
	 * @return
	 */
	public int insertInformation(Information information) ;
	
	/**
	 * 更新资料信息
	 * @param information
	 * @return
	 */
	public boolean updateInformation(Information information) ;
	
	/**
	 * 删除资料信息
	 * @param informtion
	 * @return
	 */
	public boolean delInfomation(Information informtion) ;
	
	/**
	 * 查询资料详情
	 * @param information
	 * @return
	 */
	public Information selectInfoDetail(Information information) ;
	
	/**
	 * 更新点击次数
	 * @param information
	 * @return
	 */
	public boolean updateInfoCheckNum(int infoId) ;
	
	/**
	 * 更新下载次数
	 * @param information
	 * @return
	 */
	public boolean updateInfoDownNum(int infoId) ;
	
}
