package com.autmone.softmarket.vo;

public class Comment {
	private int commentId;//'自增主键',
	private int type;// '属于哪个模块',
	private int typeId;// '模块里的ID主键',
	private int userId;// '评论人',
	private String commentContent;//'评论内容',
	private int goodNum;// '点赞次数',
	private String addTime;// '评论时间',
	private int state;// '状态，1：有效，0：失效',
	
	public Comment() {} 
	
	public Comment(int type, int typeId, int userId, String commentContent, int goodNum, String addTime, int state) {
		super();
		this.type = type;
		this.typeId = typeId;
		this.userId = userId;
		this.commentContent = commentContent;
		this.goodNum = goodNum;
		this.addTime = addTime;
		this.state = state;
	}
	
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public int getGoodNum() {
		return goodNum;
	}
	public void setGoodNum(int goodNum) {
		this.goodNum = goodNum;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", type=" + type + ", typeId=" + typeId + ", userId=" + userId
				+ ", commentContent=" + commentContent + ", goodNum=" + goodNum + ", addTime=" + addTime + ", state="
				+ state + "]";
	}
	
}
