package com.wap.trans.entity.tr_1021;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SCORE")
public class ResTrans1021Score {

	@XStreamAlias("USERID")
	private String userId;
	@XStreamAlias("JFAMOUNT")
	private Integer jfAmount;
	@XStreamAlias("VALIDDATE")
	private String validDate;
	@XStreamAlias("JFSTATUS")
	private String jfStatus;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getJfAmount() {
		return jfAmount;
	}

	public void setJfAmount(Integer jfAmount) {
		this.jfAmount = jfAmount;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getJfStatus() {
		return jfStatus;
	}

	public void setJfStatus(String jfStatus) {
		this.jfStatus = jfStatus;
	}

	
	
}
