package com.wap.dzsw.entity;

public class ServiceReOrInquiryEntity {

	private String cardNo;  //礼品编号
	
	private String fwType; 
	
	private String openId; 

	public String getFwType() {
		return fwType;
	}

	public void setFwType(String fwType) {
		this.fwType = fwType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Override
	public String toString() {
		return "ServiceReOrInquiryEntity [cardNo=" + cardNo + ", fwType="
				+ fwType + ", openId=" + openId + "]";
	}
	
	
}
