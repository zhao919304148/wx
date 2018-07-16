package com.wap.trans.entity.tr_1059;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("RECORD_ITEM")
public class ResTrans1059RecordListEntity {
	
	@XStreamAlias("NETWORKNAME")
	private String networkName;
	
	@XStreamAlias("CONSUMETIME")
	private String consumeTime;

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public String getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(String consumeTime) {
		this.consumeTime = consumeTime;
	}
	
	
}
