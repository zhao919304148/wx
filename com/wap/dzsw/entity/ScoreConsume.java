package com.wap.dzsw.entity;

import java.util.List;

import com.wap.trans.entity.tr_1029.ScoreOrder;


		/**
		
		* 描述:
		*
		* @author 朱久满
		* @version 1.0
		* @since 2017年1月23日 下午1:36:38
		*/
	
public class ScoreConsume {

	private String userId;
	private String openId;
	private String amount;
	private List<ScoreOrder> orderList;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public List<ScoreOrder> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<ScoreOrder> orderList) {
		this.orderList = orderList;
	}
	@Override
	public String toString() {
		return "ScoreConsume [userId=" + userId + ", openId=" + openId + ", amount=" + amount + ", orderList="
				+ orderList + "]";
	}
	
	
}
