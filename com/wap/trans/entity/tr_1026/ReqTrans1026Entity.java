package com.wap.trans.entity.tr_1026;

public class ReqTrans1026Entity {

	private String  licenseNo; //车牌号
	
	private String   name;   // 姓名
	
	private String   phoneNumber;     //手机
	
	public String getValadateCode() {
		return valadateCode;
	}

	public void setValadateCode(String valadateCode) {
		this.valadateCode = valadateCode;
	}

	private String valadateCode;    //验证码

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	
	
	
	
	
}
