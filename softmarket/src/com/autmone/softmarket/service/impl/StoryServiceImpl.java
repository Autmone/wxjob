package com.autmone.softmarket.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autmone.softmarket.dao.IStoryDao;
import com.autmone.softmarket.service.IStoryService;
import com.autmone.softmarket.vo.Story;

@Service
public class StoryServiceImpl implements IStoryService {

	@Autowired
	private IStoryDao storyDao ;

	@Override
	public List<Story> getStoryListByPage(Map<String, Object> condition) {
		return storyDao.selectStoryListByPage(condition);
	}

	@Override
	public int getStoryListCount(Map<String, Object> condition) {
		return storyDao.selectStoryListCount(condition);
	}

	@Override
	public int insertStory(Story story) {
		return storyDao.insertStory(story);
	}

	@Override
	public boolean updateStory(Story story) {
		return storyDao.updateStory(story);
	}

	@Override
	public boolean delStory(Story informtion) {
		return storyDao.delStory(informtion);
	}
	
	public Story getStoryDetail(Story story) {
		return storyDao.selectStoryDetail(story);
	}
	
	public boolean updateStoryCheckNum(int storyId) {
		return storyDao.updateStoryCheckNum(storyId);
	}

}
