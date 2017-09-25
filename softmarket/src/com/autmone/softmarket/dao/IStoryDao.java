package com.autmone.softmarket.dao;

import java.util.List;
import java.util.Map;

import com.autmone.softmarket.vo.Story;

public interface IStoryDao {

	/**
	 * 列表查询微小说列表
	 * @param condition
	 * @return
	 */
	public List<Story> selectStoryListByPage(Map<String,Object> condition) ;
	
	/**
	 * 查询资料微小说总条数
	 * @param condition
	 * @return
	 */
	public int selectStoryListCount(Map<String,Object> condition) ;
	
	/**
	 * 新增微小说信息
	 * @param story
	 * @return
	 */
	public int insertStory(Story story) ;
	
	/**
	 * 更新微小说信息
	 * @param story
	 * @return
	 */
	public boolean updateStory(Story story) ;
	
	/**
	 * 删除微小说信息
	 * @param informtion
	 * @return
	 */
	public boolean delStory(Story informtion) ;
	
	/**
	 * 查询微小说详情
	 * @param story
	 * @return
	 */
	public Story selectStoryDetail(Story story) ;
	
	/**
	 * 更新点击次数
	 * @param story
	 * @return
	 */
	public boolean updateStoryCheckNum(int storyId) ;
	
	
}
