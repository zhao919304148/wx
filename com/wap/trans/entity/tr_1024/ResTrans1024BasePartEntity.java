package com.wap.trans.entity.tr_1024;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BASE_PART")
public class ResTrans1024BasePartEntity {

	@XStreamAlias("ORDERNO")
	private String orderNo;   //订单号
	
	@XStreamAlias("FWTYPE")
	private String fwType; //服务类型
	
	@XStreamAlias("PHONENUMBER")
	private String phoneNumber; //手机号
	
	@XStreamAlias("LICENSENO")
	private String licenseNo;  //车牌号
	
	@XStreamAlias("FWADDRESS")
	private String fwAddress;  // 接车/交车 地点
	
	@XStreamAlias("FWDATE")
	private String fwDate;      //接车/交车 时间
	
	@XStreamAlias("NETWORKNAME")
	private String networkName;  //网点名称
	
	@XStreamAlias("NETWORKPHONE")
	private String networkPhone;  //网点电话
	
	@XStreamAlias("NETWORKADDRESS")
	private String networkAddress; //网点地址
	
	@XStreamAlias("NAME")
	private String name;//客户姓名
	
	@XStreamAlias("NETWORKJOBID")
	private String networkJobId;//网点工号ID


	public String getNetworkJobId() {
		return networkJobId;
	}

	public void setNetworkJobId(String networkJobId) {
		this.networkJobId = networkJobId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
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

	public String getFwType() {
		return fwType;
	}

	public void setFwType(String fwType) {
		this.fwType = fwType;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}


	public String getFwAddress() {
		return fwAddress;
	}

	public void setFwAddress(String fwAddress) {
		this.fwAddress = fwAddress;
	}

	public String getFwDate() {
		return fwDate;
	}

	public void setFwDate(String fwDate) {
		this.fwDate = fwDate;
	}

}
