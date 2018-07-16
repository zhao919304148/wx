package com.wap.trans.entity.tr_1038;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("IDENTIFY_DATA")
public class ResTrans1038IdentifyDateEntity {
	@XStreamAlias("IDENTIFYNO")
	private String identifyNo; //证件号
	@XStreamAlias("IDENTIFYTYPE")
	private String identifyType; //证件类型
	@XStreamAlias("PHONENUMBER")
	private String phoneNumber; //手机号
	public String getIdentifyNo() {
		return identifyNo;
	}
	public void setIdentifyNo(String identifyNo) {
		this.identifyNo = identifyNo;
	}
	public String getIdentifyType() {
		return identifyType;
	}
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}
