package com.wap.dzsw.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 网点信息实体
		
		* 描述:
		*
		* @author 朱久满
		* @version 1.0
		* @since 2017年7月10日 下午2:25:46
 */
public class NetworkEntity {
	
	private String networkName; //网点名称
	private String companyCode; //所属服务商
	private String networkAddress; //网点地址
	private String networkPhone; //网点手机号
	private String longitude; //经度
	private String latitude; //纬度
	
	public String getNetworkName() {
		return networkName;
	}
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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
