package com.wap.trans.entity.tr_1016;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("POLICY_DATA")
public class ResTrans1016PolicyDataEntity {

	@XStreamAlias("POLICYNO")
	private String policyNo;// 保单号
	@XStreamAlias("IDENTIFYTYPE")
	private String identifyType;// 证件类型
	@XStreamAlias("IDENTIFYNUMBER")
	private String identifyNumber;// 证件号
	@XStreamAlias("SUMPREMIUM")
	private String sumpremium;// 总保费
	@XStreamAlias("STARTDATE")
	private String startDate;//起保日期
	@XStreamAlias("ENDDATE")
	private String endDate;//终保日期
	@XStreamAlias("VALIDDATE")
	private String validDate;//领取有效日期
	@XStreamAlias("CLAIMFLAG")
	private String claimFlag;//出险标识
	@XStreamAlias("EXCHANGEFLAG")
	private String exchangeFlag;//兑换标识
	@XStreamAlias("EXCHANGEDATE")
	private String exchangeDate;//兑换日期
	@XStreamAlias("EXCHANGEAMOUNT")
	private String exchangeAmount;//兑换金额
	@XStreamAlias("SMSFLAG")
	private String smsFlag;//续保奖励短信
	@XStreamAlias("VALIDFLAG")
	private String validFlag;//领取有效标志
	
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
	public String getSumpremium() {
		return sumpremium;
	}
	public void setSumpremium(String sumpremium) {
		this.sumpremium = sumpremium;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getValidDate() {
		return validDate;
	}
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	public String getClaimFlag() {
		return claimFlag;
	}
	public void setClaimFlag(String claimFlag) {
		this.claimFlag = claimFlag;
	}
	public String getExchangeFlag() {
		return exchangeFlag;
	}
	public void setExchangeFlag(String exchangeFlag) {
		this.exchangeFlag = exchangeFlag;
	}
	public String getExchangeDate() {
		return exchangeDate;
	}
	public void setExchangeDate(String exchangeDate) {
		this.exchangeDate = exchangeDate;
	}
	public String getExchangeAmount() {
		return exchangeAmount;
	}
	public void setExchangeAmount(String exchangeAmount) {
		this.exchangeAmount = exchangeAmount;
	}
	public String getSmsFlag() {
		return smsFlag;
	}
	public void setSmsFlag(String smsFlag) {
		this.smsFlag = smsFlag;
	}
	public String getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
}
