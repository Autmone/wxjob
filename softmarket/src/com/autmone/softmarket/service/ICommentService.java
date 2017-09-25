package com.autmone.softmarket.service;

import java.util.List;
import java.util.Map;

import com.autmone.softmarket.vo.Comment;

public interface ICommentService {

	/**
	 * 查询用户信息
	 */
	public List<Map<String,Object>> selectCommentList(Map<String,Object> condition) ;
	
	/**
	 * 插入用户信息
	 * @param user
	 * @return
	 */
	public int insertComment(Comment comment) ;
	
}
