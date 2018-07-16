package com.wap.trans.entity.tr_1044;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("RECORD_DATA")
public class ResTrans1044RecordDataEntity {
	@XStreamAlias("NETWORKNAME")
	private String networkName; //网点名称
	@XStreamAlias("WASHCARTIME")
	private String washCarTime; //洗车时间
	
	public String getNetworkName() {
		return networkName;
	}
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
	public String getWashCarTime() {
		return washCarTime;
	}
	public void setWashCarTime(String washCarTime) {
		this.washCarTime = washCarTime;
	}
	
}
