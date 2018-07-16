package com.wap.trans.entity.tr_1017;

import java.util.List;

public class ReqTrans1017Entity {
	
	private String openid;// openid
	private String policyNo;// 保单号
	private String identifyType;// 证件类型
	private String identifyNumber;// 证件号
	private String phoneNumber;//手机号
	private String operateType;//操作类型，get:获取,qry:查询
	private List<ReqTrans1017ExchangeEntity> reqTrans1017ExchangeEntityList;// 兑换券列表
	private List<ReqTrans1017CzEntity> reqTrans1017CzEntityList;//充值列表
	
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
	public List<ReqTrans1017ExchangeEntity> getReqTrans1017ExchangeEntityList() {
		return reqTrans1017ExchangeEntityList;
	}
	public void setReqTrans1017ExchangeEntityList(
			List<ReqTrans1017ExchangeEntity> reqTrans1017ExchangeEntityList) {
		this.reqTrans1017ExchangeEntityList = reqTrans1017ExchangeEntityList;
	}
	public List<ReqTrans1017CzEntity> getReqTrans1017CzEntityList() {
		return reqTrans1017CzEntityList;
	}
	public void setReqTrans1017CzEntityList(
			List<ReqTrans1017CzEntity> reqTrans1017CzEntityList) {
		this.reqTrans1017CzEntityList = reqTrans1017CzEntityList;
	}
}
