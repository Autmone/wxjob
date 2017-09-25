package com.autmone.softmarket.vo;

public class Message {

	private int msgId;//'自增主键',
	private String content;//'消息内容',
	private int fromUserId;//'发送方ID',
	private int toUserId;//'接收方ID',
	private int isView;//'是否已读',
	private int msgPid;//'属于哪条消息的回复',
	private int state;//'消息是否有效,0:失效 1:有效',
	private String addTime;//'添加时间',
	
	public Message(String content, int fromUserId, int toUserId, int isView, int msgPid, int state, String addTime) {
		super();
		this.content = content;
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
		this.isView = isView;
		this.msgPid = msgPid;
		this.state = state;
		this.addTime = addTime;
	}
	
	public int getMsgId() {
		return msgId;
	}
	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}
	public int getToUserId() {
		return toUserId;
	}
	public void setToUserId(int toUserId) {
		this.toUserId = toUserId;
	}
	public int getIsView() {
		return isView;
	}
	public void setIsView(int isView) {
		this.isView = isView;
	}
	public int getMsgPid() {
		return msgPid;
	}
	public void setMsgPid(int msgPid) {
		this.msgPid = msgPid;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	
}
