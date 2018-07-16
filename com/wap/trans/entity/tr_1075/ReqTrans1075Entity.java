package com.wap.trans.entity.tr_1075;
/**
* 
* 描述:1075 提交测算信息
* @author 赵硕
* @version 1.0
* @since 2018年4月12日 上午10:57:49
 */
public class ReqTrans1075Entity {
	private String referrerOpenid;//推荐人openid
	
	private String licenseNo;//车牌号
	
	private String phoneNo;//手机号
	
	private String measurependid;//测算人
	
	private String identifyType;//证件类型
	
	private String identifyNo;//证件号
	
	private String verifyCode;//验证码

	public String getReferrerOpenid() {
		return referrerOpenid;
	}

	public void setReferrerOpenid(String referrerOpenid) {
		this.referrerOpenid = referrerOpenid;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getMeasurependid() {
		return measurependid;
	}

	public void setMeasurependid(String measurependid) {
		this.measurependid = measurependid;
	}

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

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	@Override
	public String toString() {
		return "ReqTrans1074Entity [referrerOpenid=" + referrerOpenid
				+ ", licenseNo=" + licenseNo + ", phoneNo=" + phoneNo
				+ ", measurependid=" + measurependid + ", identifyType="
				+ identifyType + ", identifyNo=" + identifyNo + ", verifyCode="
				+ verifyCode + "]";
	}

}
