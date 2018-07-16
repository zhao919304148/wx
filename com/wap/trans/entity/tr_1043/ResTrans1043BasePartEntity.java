package com.wap.trans.entity.tr_1043;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ResTrans1043BasePartEntity {
	@XStreamAlias("WASHCARCODE")
	private String washCarCode;//	洗车码	String		是	待验证洗车码
	@XStreamAlias("NETWORKNAME")
	private String networkName;//	网点名称	String		是	消费网点名称
	@XStreamAlias("NETWORKADDRESS")
	private String networkAddress;//	网店地址	String		是	消费网点地址
	@XStreamAlias("TRANSPORTTIME")
	private String transportTime;//	传输时间	String 		是	日期格式： yyyy-MM-dd HH:mm:ss.SSS 如:2017-07-07 08:08:08.888
	@XStreamAlias("HANDLETIME")
	private String handleTime;//	处理时间	String		是	日期格式：yyyy-MM-dd HH:mm:ss.SSS 如:2017-07-07 08:08:08.888
	@XStreamAlias("THIRDORDERID")
	private String thirdOrderId;//	第三方系统订单号	String		是	外部系统订单号
	public String getWashCarCode() {
		return washCarCode;
	}
	public void setWashCarCode(String washCarCode) {
		this.washCarCode = washCarCode;
	}
	public String getNetworkName() {
		return networkName;
	}
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
	public String getNetworkAddress() {
		return networkAddress;
	}
	public void setNetworkAddress(String networkAddress) {
		this.networkAddress = networkAddress;
	}
	public String getTransportTime() {
		return transportTime;
	}
	public void setTransportTime(String transportTime) {
		this.transportTime = transportTime;
	}
	public String getHandleTime() {
		return handleTime;
	}
	public void setHandleTime(String handleTime) {
		this.handleTime = handleTime;
	}
	public String getThirdOrderId() {
		return thirdOrderId;
	}
	public void setThirdOrderId(String thirdOrderId) {
		this.thirdOrderId = thirdOrderId;
	}
}
