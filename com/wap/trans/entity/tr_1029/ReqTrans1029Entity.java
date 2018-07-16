package com.wap.trans.entity.tr_1029;

import java.util.List;

public class ReqTrans1029Entity {

	private String userId;
	private String amount;
	private String openId;
	private List<ScoreOrder> orderList;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public List<ScoreOrder> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<ScoreOrder> orderList) {
		this.orderList = orderList;
	}
	@Override
	public String toString() {
		return "ReqTrans1029Entity [userId=" + userId + ", amount=" + amount + ", openId=" + openId + ", orderList="
				+ orderList + "]";
	}
	
	
}
