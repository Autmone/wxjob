package com.autmone.softmarket.vo;

public class WXMessage {

	private String ToUserName = "";//	开发者微信号
	private String FromUserName;//	发送方帐号（一个OpenID）
	private Long CreateTime;//	消息创建时间 （整型）
	private String MsgType;//	语音为voice
	private String MediaID;//	语音消息媒体id，可以调用多媒体文件下载接口拉取该媒体
	private String Format;//	语音格式：amr
	private String Recognition;//	语音识别结果，UTF8编码
	private Long MsgID;//	消息id，64位整型
	private String Content; //文本消息内容
	
	public WXMessage() {}
	
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public String getMediaID() {
		return MediaID;
	}
	public void setMediaID(String mediaID) {
		MediaID = mediaID;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
	public String getRecognition() {
		return Recognition;
	}
	public void setRecognition(String recognition) {
		Recognition = recognition;
	}
	public Long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}
	public Long getMsgID() {
		return MsgID;
	}
	public void setMsgID(Long msgID) {
		MsgID = msgID;
	}
	
	
}
