package com.wap.trans.entity.tr_1040;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("NETWORK_DATA")
public class ResTrans1040NetworkDataEntity {
	@XStreamAlias("NETWORKNAME")
	private String networkName; //网点名称
	@XStreamAlias("COMPANYCODE")
	private String companyCode; //所属服务商
	@XStreamAlias("NETWORKADDRESS")
	private String networkAddress; //网点地址
	@XStreamAlias("NETWORKPHONE")
	private String networkPhone; //网点手机号
	@XStreamAlias("LONGITUDE")
	private String longitude; //经度
	@XStreamAlias("LATITUDE")
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
