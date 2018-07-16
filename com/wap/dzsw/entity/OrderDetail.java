package com.wap.dzsw.entity;

/**
 * 订单详情
		
		* 描述:
		*
		* @author qex
		* @version 1.0
		* @since 2016年12月8日 上午10:46:39
 */
public class OrderDetail {

	private String orderNo; 
	private String fwType ;
	private String phoneNumber ;
	private String licenseNo ;
	private String fwAddress ;
	private String fwDate ;
	private String networkName ;
	private String networkPhone ;
	private String networkAddress ;
	private String fwStatus ;
	public String getFwType() {
		return fwType;
	}
	public void setFwType(String fwType) {
		this.fwType = fwType;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public String getFwAddress() {
		return fwAddress;
	}
	public void setFwAddress(String fwAddress) {
		this.fwAddress = fwAddress;
	}
	public String getFwDate() {
		return fwDate;
	}
	public void setFwDate(String fwDate) {
		this.fwDate = fwDate;
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
	public String getFwStatus() {
		return fwStatus;
	}
	public void setFwStatus(String fwStatus) {
		this.fwStatus = fwStatus;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	

}
