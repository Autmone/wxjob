package com.autmone.softmarket.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autmone.softmarket.dao.ICommentDao;
import com.autmone.softmarket.service.ICommentService;
import com.autmone.softmarket.vo.Comment;

@Service
public class CommentServiceImpl implements ICommentService {

	@Autowired
	private ICommentDao commentDao ;
	
	@Override
	public List<Map<String,Object>> selectCommentList(Map<String, Object> condition) {
		return commentDao.selectCommentList(condition);
	}

	@Override
	public int insertComment(Comment comment) {
		return commentDao.insertComment(comment);
	}

}
