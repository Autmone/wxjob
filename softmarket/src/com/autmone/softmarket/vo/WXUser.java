package com.autmone.softmarket.vo;

public class WXUser {

	private int wxUserId;
	private String wxNickname;
	private String openid;
	private String sessionKey;
	private String avatarUrl;
	private String gender;
	private String province;
	private String city;
	private String country;
	private String addTime;
	
	public WXUser() {}
	
	public WXUser(String wxNickname, String openid, String sessionKey, String avatarUrl, String gender, String province,
			String city, String country, String addTime) {
		super();
		this.wxNickname = wxNickname;
		this.openid = openid;
		this.sessionKey = sessionKey;
		this.avatarUrl = avatarUrl;
		this.gender = gender;
		this.province = province;
		this.city = city;
		this.country = country;
		this.addTime = addTime;
	}
	public int getWxUserId() {
		return wxUserId;
	}
	public void setWxUserId(int wxUserId) {
		this.wxUserId = wxUserId;
	}
	public String getWxNickname() {
		return wxNickname;
	}
	public void setWxNickname(String wxNickname) {
		this.wxNickname = wxNickname;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	
	@Override
	public String toString() {
		return "WXUser [wxUserId=" + wxUserId + ", wxNickname=" + wxNickname + ", openid=" + openid + ", sessionKey="
				+ sessionKey + ", avatarUrl=" + avatarUrl + ", gender=" + gender + ", province=" + province + ", city="
				+ city + ", country=" + country + ", addTime=" + addTime + "]";
	}
	
}
