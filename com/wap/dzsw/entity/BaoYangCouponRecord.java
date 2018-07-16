package com.wap.dzsw.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
		
		* 描述:维修保养劵消费记录实体
		*
		* @author 赵硕
		* @version 1.0
		* @since 2017年12月12日 下午1:36:47
 */
public class BaoYangCouponRecord {
	private String networkName;// 网点名称
	private String networkJobid;// 网点工号
	private String networkPhone;// 网点手机号
	private String networkAddress;// 网点地址
	private String consumeTime;//服务时间
	private String consumeAmount;//消费金额
	public String getNetworkName() {
		return networkName;
	}
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
	public String getNetworkJobid() {
		return networkJobid;
	}
	public void setNetworkJobid(String networkJobid) {
		this.networkJobid = networkJobid;
	}
	public String getNetworkPhone() {
		return networkPhone;
	}
	public void setNetworkPhone(String networkPhone) {
		this.networkPhone = networkPhone;
	}
	public String getNetworkAddress() {
		return networkAddress;
	}
	public void setNetworkAddress(String networkAddress) {
		this.networkAddress = networkAddress;
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
