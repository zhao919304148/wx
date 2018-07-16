package com.wap.trans.entity.tr_1061;

import com.thoughtworks.xstream.annotations.XStreamAlias;


public class ResTrans1061CardOrderDataEntity {
	@XStreamAlias("CARDID")
	private String cardId;//卡号
	
	@XStreamAlias("PASSWORD")
	private String passWord;//密码
	
	@XStreamAlias("LICENSENO")
	private String licenseNo;//车牌号
	
	@XStreamAlias("RESERVEPHONE")
	private String reservePhone;//预约人电话
	
	@XStreamAlias("NETWORKNAME")
	private String networkName;//服务网点
	
	@XStreamAlias("SERVICEADDRESS")
	private String serviceAddress;//服务地址
	
	@XStreamAlias("OILTYPE")
	private String oilType;//机油型号
	
	@XStreamAlias("MACHINETYPE")
	private String machineType;//机滤型号
	
	@XStreamAlias("ADDSERVICE")
	private String addService;//增值服务
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public String getReservePhone() {
		return reservePhone;
	}
	public void setReservePhone(String reservePhone) {
		this.reservePhone = reservePhone;
	}
	public String getNetworkName() {
		return networkName;
	}
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
	public String getServiceAddress() {
		return serviceAddress;
	}
	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}
	public String getOilType() {
		return oilType;
	}
	public void setOilType(String oilType) {
		this.oilType = oilType;
	}
	public String getMachineType() {
		return machineType;
	}
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	public String getAddService() {
		return addService;
	}
	public void setAddService(String addService) {
		this.addService = addService;
	}
	
}
