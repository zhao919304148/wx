package com.wap.kfcenter.entity;

import java.util.List;

public class ExchangeReqEntity {

	// openid
	private String openid = "";
	//保单号
	private String policyNo = "";
	// 证件类型
	private String identifyType = "";
	// 证件号
	private String identifyNumber = "";
	//手机号
	private String phoneNumber = "";
	//交易类型
	private String operateType = "";
	//兑换券列表
	private List<ExchangeReqListEntity> exchangeReqList;
	//充值列表
	private List<CzReqListEntity> czReqList;
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getIdentifyType() {
		return identifyType;
	}
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}
	public String getIdentifyNumber() {
		return identifyNumber;
	}
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public List<ExchangeReqListEntity> getExchangeReqList() {
		return exchangeReqList;
	}
	public void setExchangeReqList(List<ExchangeReqListEntity> exchangeReqList) {
		this.exchangeReqList = exchangeReqList;
	}
	public List<CzReqListEntity> getCzReqList() {
		return czReqList;
	}
	public void setCzReqList(List<CzReqListEntity> czReqList) {
		this.czReqList = czReqList;
	}
}
