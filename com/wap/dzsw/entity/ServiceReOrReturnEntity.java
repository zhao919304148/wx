package com.wap.dzsw.entity;

import java.util.Date;

public class ServiceReOrReturnEntity {

	private String fwType; //服务类型
	private String phoneNumber; //手机号
	private String licenseNo;  //车牌号
	private String fwaddRess;  // 接车/交车 地点
	private Date fwDate;      //接车/交车 时间
	private String jccaddRess;  //检测场
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
	public String getFwaddRess() {
		return fwaddRess;
	}
	public void setFwaddRess(String fwaddRess) {
		this.fwaddRess = fwaddRess;
	}
	public Date getFwDate() {
		return fwDate;
	}
	public void setFwDate(Date fwDate) {
		this.fwDate = fwDate;
	}
	public String getJccaddRess() {
		return jccaddRess;
	}
	public void setJccaddRess(String jccaddRess) {
		this.jccaddRess = jccaddRess;
	}
	
	
}
