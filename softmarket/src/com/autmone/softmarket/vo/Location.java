package com.autmone.softmarket.vo;

public class Location {
	private int locationId; //'自增主键',
	private double latitude;//'纬度，浮点数，范围为-90~90，负数表示南纬',
	private double longitude;//'经度，浮点数，范围为-180~180，负数表示西经',
	private double accuracy;//'位置的精确度',
	private double altitude;//'高度，单位 m',
	private double verticalAccuracy;//'垂直精度，单位 m（Android 无法获取，返回 0）',
	private double horizontalAccuracy;//'水平精度，单位 m',
	private int wxUserId;//'用户ID',
	private int checkNu;
	private String addTime;
	private String updateTime;
	
	public Location() {}
	
	public Location(double latitude, double longitude, double accuracy, double altitude, double verticalAccuracy,
			double horizontalAccuracy, int wxUserId, String addTime) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.accuracy = accuracy;
		this.altitude = altitude;
		this.verticalAccuracy = verticalAccuracy;
		this.horizontalAccuracy = horizontalAccuracy;
		this.wxUserId = wxUserId;
		this.addTime = addTime;
	}


	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	public double getVerticalAccuracy() {
		return verticalAccuracy;
	}
	public void setVerticalAccuracy(double verticalAccuracy) {
		this.verticalAccuracy = verticalAccuracy;
	}
	public double getHorizontalAccuracy() {
		return horizontalAccuracy;
	}
	public void setHorizontalAccuracy(double horizontalAccuracy) {
		this.horizontalAccuracy = horizontalAccuracy;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public int getWxUserId() {
		return wxUserId;
	}

	public void setWxUerId(int wxUserId) {
		this.wxUserId = wxUserId;
	}

	public int getCheckNu() {
		return checkNu;
	}

	public void setCheckNu(int checkNu) {
		this.checkNu = checkNu;
	}
	
	
}
