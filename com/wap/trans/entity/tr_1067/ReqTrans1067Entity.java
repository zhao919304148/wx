package com.wap.trans.entity.tr_1067;

public class ReqTrans1067Entity {
	
	private String orderNo;//我方订单号
	private String maintainType;//保养方式	MAINTAINTYPE	字符串(1)	Y	0、上门保养 1、到店保养
	private String licenseNo;//服务车辆             	LICENSENO	字符串(20)	Y	
	private String reservePhone;//预约人电话	 RESERVEPHONE	字符串(20)	Y	
	private String serviceDate;//服务日期	SERVICEDATE	日期	Y	日期格式 yyyy-MM-dd HH:mm:ss
	private String networkName;//服务网点	NETWORKNAME        	字符串(60)	Y	保养方式：到店保养时，有值
	private String serviceAddress;//服务地址	NETWORKADDRESS	字符串(60)	Y	保养方式：上门保养 时，有值
	private String oilType;//机油型号	OILTYPE	字符串(30)	Y	
	private String machineType;//机滤型号	MACHINETYPE	字符串(30)	Y	
	private String addService;//增值服务	ADDSERVICE	字符串(30)	Y	
	private String fwsCode;
	
	
	public String getFwsCode() {
		return fwsCode;
	}
	public void setFwsCode(String fwsCode) {
		this.fwsCode = fwsCode;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public String getMaintainType() {
		return maintainType;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public String getReservePhone() {
		return reservePhone;
	}
	public String getServiceDate() {
		return serviceDate;
	}
	public String getNetworkName() {
		return networkName;
	}
	public String getServiceAddress() {
		return serviceAddress;
	}
	public String getOilType() {
		return oilType;
	}
	public String getMachineType() {
		return machineType;
	}
	public String getAddService() {
		return addService;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public void setReservePhone(String reservePhone) {
		this.reservePhone = reservePhone;
	}
	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}
	public void setOilType(String oilType) {
		this.oilType = oilType;
	}
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	public void setAddService(String addService) {
		this.addService = addService;
	}
	
}
