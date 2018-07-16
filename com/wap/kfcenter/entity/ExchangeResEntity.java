package com.wap.kfcenter.entity;

import java.util.List;

public class ExchangeResEntity {

	// 兑换券列表
	private List<ExchangeResListEntity> exchangeResList;
	// 充值列表
	private List<CzResListEntity> czResList;
	
	public List<ExchangeResListEntity> getExchangeResList() {
		return exchangeResList;
	}
	public void setExchangeResList(List<ExchangeResListEntity> exchangeResList) {
		this.exchangeResList = exchangeResList;
	}
	public List<CzResListEntity> getCzResList() {
		return czResList;
	}
	public void setCzResList(List<CzResListEntity> czResList) {
		this.czResList = czResList;
	}
}
