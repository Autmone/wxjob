package com.autmone.softmarket.vo;

import java.util.Date;

/**
 * 资料软件
 *
 */
public class Information {
	
	private int infoId ;	//'自增主键',
	private String infoTitle ;	//'资料标题',
	private String infoContent;	//'资料介绍',
	private String infoUrl;	//'资料URL链接',
	private int isPassword ;	// '是否需要密码，1：需要，0：不需要',
	private int isFree ;	// '该资料第三方是否收费',
	private Date addTime ;	// '添加时间',
	private Date updateTime ;	// '修改时间',
	private int checkNum ;	//'点击次数',
	private int downloadNum ;	// '下载次数',
	private int infoType ; 	//资料类型。1：软件，2：文本资料，3：视频资料
	private String infoNum;	//资料编号
	private String infoPassword;	//资料密码
	
	public Information() {}
	
	public Information(String infoTitle, String infoContent, String infoUrl, int isPassword, int isFree, Date addTime,
			int infoType, String infoNum, String infoPassword) {
		super();
		this.infoTitle = infoTitle;
		this.infoContent = infoContent;
		this.infoUrl = infoUrl;
		this.isPassword = isPassword;
		this.isFree = isFree;
		this.addTime = addTime;
		this.infoType = infoType;
		this.infoNum = infoNum;
		this.infoPassword = infoPassword;
	}
	public int getInfoId() {
		return infoId;
	}
	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}
	public String getInfoTitle() {
		return infoTitle;
	}
	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}
	public String getInfoContent() {
		return infoContent;
	}
	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent;
	}
	public String getInfoUrl() {
		return infoUrl;
	}
	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}
	public int getIsPassword() {
		return isPassword;
	}
	public void setIsPassword(int isPassword) {
		this.isPassword = isPassword;
	}
	public int getIsFree() {
		return isFree;
	}
	public void setIsFree(int isFree) {
		this.isFree = isFree;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getCheckNum() {
		return checkNum;
	}
	public void setCheckNum(int checkNum) {
		this.checkNum = checkNum;
	}
	public int getDownloadNum() {
		return downloadNum;
	}
	public void setDownloadNum(int downloadNum) {
		this.downloadNum = downloadNum;
	}
	public int getInfoType() {
		return infoType;
	}
	public void setInfoType(int infoType) {
		this.infoType = infoType;
	}
	public String getInfoNum() {
		return infoNum;
	}
	public void setInfoNum(String infoNum) {
		this.infoNum = infoNum;
	}
	public String getInfoPassword() {
		return infoPassword;
	}
	public void setInfoPassword(String infoPassword) {
		this.infoPassword = infoPassword;
	}
	
	@Override
	public String toString() {
		return "Information [infoId=" + infoId + ", infoTitle=" + infoTitle + ", infoContent=" + infoContent
				+ ", infoUrl=" + infoUrl + ", isPassword=" + isPassword + ", isFree=" + isFree + ", addTime=" + addTime
				+ ", updateTime=" + updateTime + ", checkNum=" + checkNum + ", downloadNum=" + downloadNum + "]";
	}
}
