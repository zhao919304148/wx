package com.wap.dzsw.entity;
/**
 * 
		
		* 描述:保养维修网点信息实体
		*
		* @author 赵硕
		* @version 1.0
		* @since 2017年12月12日 下午1:40:51
 */
public class BaoYangNetworkEntity {
	private String networkJobId;//网点工号
	private String networkName; //网点名称
	private String networkAddress; //网点地址
	private String networkPhone; //网点手机号
	private String longitude; //经度
	private String latitude; //纬度
	public String getNetworkJobId() {
		return networkJobId;
	}
	public void setNetworkJobId(String networkJobId) {
		this.networkJobId = networkJobId;
	}
	public String getNetworkName() {
		return networkName;
	}
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
	public String getNetworkAddress() {
		return networkAddress;
	}
	public void setNetworkAddress(String networkAddress) {
		this.networkAddress = networkAddress;
	}
	public String getNetworkPhone() {
		return networkPhone;
	}
	public void setNetworkPhone(String networkPhone) {
		this.networkPhone = networkPhone;
	}
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
	
}
