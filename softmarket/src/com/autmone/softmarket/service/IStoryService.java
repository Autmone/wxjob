package com.autmone.softmarket.service;

import java.util.List;
import java.util.Map;

import com.autmone.softmarket.vo.Story;

public interface IStoryService {

	/**
	 * 列表查询资料软件列表	
	 * @param condition
	 * @return
	 */
	public List<Story> getStoryListByPage(Map<String,Object> condition) ;
	
	/**
	 * 查询资料软件列表总条数
	 * @param condition
	 * @return
	 */
	public int getStoryListCount(Map<String,Object> condition) ;
	
	/**
	 * 新增资料信息
	 * @param story
	 * @return
	 */
	public int insertStory(Story story) ;
	
	/**
	 * 更新资料信息
	 * @param story
	 * @return
	 */
	public boolean updateStory(Story story) ;
	
	/**
	 * 删除资料信息
	 * @param informtion
	 * @return
	 */
	public boolean delStory(Story informtion) ;
	
	/**
	 * 查询资料详情
	 * @param story
	 * @return
	 */
	public Story getStoryDetail(Story story) ;
	
	/**
	 * 更新点击次数
	 * @param story
	 * @return
	 */
	public boolean updateStoryCheckNum(int storyId) ;
	
}
