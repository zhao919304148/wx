package com.wap.dzsw.entity;

public class CancelOrder {

	private String cardNo;
	private String fwType;
	private String networkJobId;
	private String yuYueDate;
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
	public String getNetworkJobId() {
		return networkJobId;
	}
	public void setNetworkJobId(String networkJobId) {
		this.networkJobId = networkJobId;
	}
	public String getYuYueDate() {
		return yuYueDate;
	}
	public void setYuYueDate(String yuYueDate) {
		this.yuYueDate = yuYueDate;
	}
	@Override
	public String toString() {
		return "CancelOrder [cardNo=" + cardNo + ", fwType=" + fwType + ", networkJobId=" + networkJobId
				+ ", yuYueDate=" + yuYueDate + "]";
	}
	

}
