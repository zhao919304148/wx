package com.wap.dzsw.entity;

public class RbOrder {

	private String cardNo;
	
	private String fwType;
	
	private String openId;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getFwType() {
		return fwType;
	}

	public void setFwType(String fwType) {
		this.fwType = fwType;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Override
	public String toString() {
		return "RbOrder [cardNo=" + cardNo + ", fwType=" + fwType + ", openId="
				+ openId + "]";
	}
	
	
}
