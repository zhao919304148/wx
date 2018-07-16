package com.wap.dzsw.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
		
		* 描述:证件信息vo
		*
		* @author 
		* @version 1.0
		* @since 2018年2月27日 下午1:53:59
 */
public class IdentifyDateEntity {

	private String identifyNo; //证件号
	
	private String identifyType; //证件类型
	
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
