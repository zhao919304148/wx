package com.wap.dzsw.entity;

public class UpdateOrder {

	private String cardNo;  //礼品id
	
	private String cardStatus;  // 礼品状态
	
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	@Override
	public String toString() {
		return "UpdateOrder [cardNo=" + cardNo + ", cardStatus=" + cardStatus + "]";
	}

	
}
