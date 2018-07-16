package com.wap.trans.entity.tr_1018;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("EXCHANGE_DATA")
public class ResTrans1018ExchangeDataEntity {
	
	@XStreamAlias("EXCHANGECODE")
	private String exchangeCode;// 兑换码
	@XStreamAlias("EXCHANGEPASS")
	private String exchangePass;// 兑换密码
	@XStreamAlias("EXCHANGEPRICE")
	private String exchangePrice;// 兑换金额
	@XStreamAlias("EXPDATE")
	private String expDate;// 有效期
	
	public String getExchangeCode() {
		return exchangeCode;
	}
	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}
	public String getExchangePass() {
		return exchangePass;
	}
	public void setExchangePass(String exchangePass) {
		this.exchangePass = exchangePass;
	}
	public String getExchangePrice() {
		return exchangePrice;
	}
	public void setExchangePrice(String exchangePrice) {
		this.exchangePrice = exchangePrice;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
}
