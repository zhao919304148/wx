package com.wap.dzsw.entity;

public class WashCarRecord {
	/**网点名称**/
	private String networkName;
	/**洗车时间**/
	private String washCarTime;
	private String consumeTime;
	
	public String getConsumeTime() {
		return consumeTime;
	}
	public void setConsumeTime(String consumeTime) {
		this.consumeTime = consumeTime;
	}
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
