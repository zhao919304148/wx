package com.wap.trans.entity.tr_1060;

public class ReqTrans1060Entity {
	private String cardId;//券号	CARDID	字符串(30)	Y	
	private String password;//密码	PASSWORD	字符串(30)	Y	
	private String maintainType;//保养方式	MAINTAINTYPE	字符串(1)	Y	0、上门保养 1、到店保养
	private String fwsCode;//服务商代码	FWSCODE	字符串(30)	Y	
	private String licenseNo;//服务车辆             	LICENSENO	字符串(20)	Y	
	private String reservePhone;//预约人电话	 RESERVEPHONE	字符串(20)	Y	
	private String serviceDate;//服务日期	SERVICEDATE	日期	Y	日期格式 yyyy-MM-dd HH:mm:ss
	private String networkName;//服务网点	NETWORKNAME        	字符串(60)	Y	保养方式：到店保养时，有值
	private String serviceAddress;//服务地址	NETWORKADDRESS	字符串(60)	Y	保养方式：上门保养 时，有值
	private String oilType;//机油型号	OILTYPE	字符串(30)	Y	
	private String machineType;//机滤型号	MACHINETYPE	字符串(30)	Y	
	private String addService;//增值服务	ADDSERVICE	字符串(30)	Y	
	private String openId;//客户微信号	OPENID	字符串(30)	Y	
	private String thirdOrderNo;//第三方订单号 字符串（30） Y
	private String isBackend;//是否为后端调用   字符串   N
	private String isRealCard;// 是否为实体卡 “true”:是；否则为否；可不填默认否

	
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMaintainType() {
		return maintainType;
	}
	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}
	public String getFwsCode() {
		return fwsCode;
	}
	public void setFwsCode(String fwsCode) {
		this.fwsCode = fwsCode;
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
	public String getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
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
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getThirdOrderNo() {
		return thirdOrderNo;
	}
	public String getIsBackend() {
		return isBackend;
	}
	public void setThirdOrderNo(String thirdOrderNo) {
		this.thirdOrderNo = thirdOrderNo;
	}
	public void setIsBackend(String isBackend) {
		this.isBackend = isBackend;
	}
	public String getIsRealCard() {
		return isRealCard;
	}
	public void setIsRealCard(String isRealCard) {
		this.isRealCard = isRealCard;
	}
	
}
