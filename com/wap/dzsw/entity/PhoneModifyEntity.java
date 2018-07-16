package com.wap.dzsw.entity;

public class PhoneModifyEntity {
	
	/**证件类型**/
	private String identifyType;
	/**证件号码**/
	private String identifyNo;
	/**车牌号码**/
	private String licenseNo;
	/**用户名**/
	private String userName;
	/**变更后的手机号**/
	private String modifyPhoneNumber;
	public String getIdentifyType() {
		return identifyType;
	}
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}
	public String getIdentifyNo() {
		return identifyNo;
	}
	public void setIdentifyNo(String identifyNo) {
		this.identifyNo = identifyNo;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getModifyPhoneNumber() {
		return modifyPhoneNumber;
	}
	public void setModifyPhoneNumber(String modifyPhoneNumber) {
		this.modifyPhoneNumber = modifyPhoneNumber;
	}
	
}
