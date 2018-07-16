package com.wap.kfcenter.entity;

import java.util.List;

public class ExchangeQueryResEntity {

	// 兑换券列表
	private List<ExchangeQueryResListEntity> exchangeResList;
	// 充值列表
	private List<CzQueryResListEntity> czResList;

	public List<ExchangeQueryResListEntity> getExchangeResList() {
		return exchangeResList;
	}

	public void setExchangeResList(
			List<ExchangeQueryResListEntity> exchangeResList) {
		this.exchangeResList = exchangeResList;
	}

	public List<CzQueryResListEntity> getCzResList() {
		return czResList;
	}

	public void setCzResList(List<CzQueryResListEntity> czResList) {
		this.czResList = czResList;
	}

}
