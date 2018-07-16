package com.wap.dzsw.entity;

public class BrandNetwork {

	private String networkName;
	private String networkPhone;
	private String networkAddress;
	private int is4S;
	private String longitude;      //经度
	private String latitude;      //纬度
	private String brandType;   //服务品牌类型  --1 专修品牌  --0 兼修品牌
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getNetworkName() {
		return networkName;
	}
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
	public String getNetworkPhone() {
		return networkPhone;
	}
	public void setNetworkPhone(String networkPhone) {
		this.networkPhone = networkPhone;
	}
	public String getNetworkAddress() {
		return networkAddress;
	}
	public void setNetworkAddress(String networkAddress) {
		this.networkAddress = networkAddress;
	}
	public int getIs4S() {
		return is4S;
	}
	public void setIs4S(int is4s) {
		is4S = is4s;
	}
	public String getBrandType() {
		return brandType;
	}
	public void setBrandType(String brandType) {
		this.brandType = brandType;
	}
	@Override
	public String toString() {
		return "BrandNetwork [networkName=" + networkName + ", networkPhone="
				+ networkPhone + ", networkAddress=" + networkAddress
				+ ", is4S=" + is4S + ", longitude=" + longitude + ", latitude="
				+ latitude + ", brandType=" + brandType + "]";
	}
	
}
