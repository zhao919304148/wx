package com.wap.trans.entity.tr_1058;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("COMSUME_ITEM")
public class ResTrans1058CardDataEntity {
	@XStreamAlias("NETWORKNAME")
	private String netWorkName;//网点名称
	
	@XStreamAlias("CONSUMETIME")
	private String consumeTime;//服务时间
	
	@XStreamAlias("CONSUMEAMOUNT")
	private String consumeAmount;//消费金额

	public String getNetWorkName() {
		return netWorkName;
	}

	public void setNetWorkName(String netWorkName) {
		this.netWorkName = netWorkName;
	}

	public String getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(String consumeTime) {
		this.consumeTime = consumeTime;
	}

	public String getConsumeAmount() {
		return consumeAmount;
	}

	public void setConsumeAmount(String consumeAmount) {
		this.consumeAmount = consumeAmount;
	}


	
	
	

}
