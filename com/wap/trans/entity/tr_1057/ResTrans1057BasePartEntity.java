package com.wap.trans.entity.tr_1057;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ResTrans1057BasePartEntity {
	@XStreamAlias("CONSUMETIME")
	private String consumeTime;//服务时间
	@XStreamAlias("REMAINAMOUNT")
	private String remainAmount;//账户余额
	public String getConsumeTime() {
		return consumeTime;
	}
	public void setConsumeTime(String consumeTime) {
		this.consumeTime = consumeTime;
	}
	public String getRemainAmount() {
		return remainAmount;
	}
	public void setRemainAmount(String remainAmount) {
		this.remainAmount = remainAmount;
	}

}
