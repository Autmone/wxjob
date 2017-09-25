package com.autmone.softmarket.vo;

public class Story {

	private int storyId ;//'自增主键',
	private String storyTitle ;//'标题',
	private String storySummary ;// '概要',
	private String storyContent ;//'内容',
	private String storyAuthor ;//'作者',
	private int storyType ;//'类型',
	private String addTime ;// '添加时间',
	private int isVip ; //是否需要VIP
	private int checkNum ;
	
	public Story() {}
	
	public Story(String storyTitle, String storySummary, String storyContent, String storyAuthor, int storyType,
			String addTime) {
		super();
		this.storyTitle = storyTitle;
		this.storySummary = storySummary;
		this.storyContent = storyContent;
		this.storyAuthor = storyAuthor;
		this.storyType = storyType;
		this.addTime = addTime;
	}
	
	public int getStoryId() {
		return storyId;
	}
	public void setStoryId(int storyId) {
		this.storyId = storyId;
	}
	public String getStoryTitle() {
		return storyTitle;
	}
	public void setStoryTitle(String storyTitle) {
		this.storyTitle = storyTitle;
	}
	public String getStorySummary() {
		return storySummary;
	}
	public void setStorySummary(String storySummary) {
		this.storySummary = storySummary;
	}
	public String getStoryContent() {
		return storyContent;
	}
	public void setStoryContent(String storyContent) {
		this.storyContent = storyContent;
	}
	public String getStoryAuthor() {
		return storyAuthor;
	}
	public void setStoryAuthor(String storyAuthor) {
		this.storyAuthor = storyAuthor;
	}
	public int getStoryType() {
		return storyType;
	}
	public void setStoryType(int storyType) {
		this.storyType = storyType;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public int getIsVip() {
		return isVip;
	}

	public void setIsVip(int isVip) {
		this.isVip = isVip;
	}

	public int getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(int checkNum) {
		this.checkNum = checkNum;
	}

	@Override
	public String toString() {
		return "Story [storyId=" + storyId + ", storyTitle=" + storyTitle + ", storySummary=" + storySummary
				+ ", storyContent=" + storyContent + ", storyAuthor=" + storyAuthor + ", storyType=" + storyType
				+ ", addTime=" + addTime + ", isVip=" + isVip + "]";
	}

	
}
