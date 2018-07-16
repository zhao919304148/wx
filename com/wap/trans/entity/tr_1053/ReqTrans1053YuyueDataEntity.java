package com.wap.trans.entity.tr_1053;

import java.util.List;


public class ReqTrans1053YuyueDataEntity {
	
	/******公共部分*******/
	//车牌号
    private String licenseno;
    
    //服务代码
    private String serviceCode;
    
    //服务名称
    private String serviceName;
    
    //服务类型
    private String serviceType;
    
    //CUSTOMERID
    private String customerID;
    
    //CUSTOMERID
    private String custserviceID;
    
    //客户姓名
    private String customerName;
    
    //客户手机号
    private String customerMobile;
    
    //操作人工号(默认：wx_auto（共同）)
    private String registCode;
    
    //联系人
    private String linkerName;
    
    //联系人手机
    private String linkerMobile;
    
    //备注
    private String remark;
    
    //预约时间（格式:yyyy-MM-dd HH:mm:ss(代驾、保养、代办验车、代办理赔)）
    private String appointment;
    
    /***分项部分***/
    //车辆品牌-(保养、代办验车、代办理赔)
    private String brandName;

    //起始地址（代驾、保养、代办验车、代办理赔）
    private String startAddress;
    
    //终点地址(代驾)
    private String endAddress;
    
    //发动机号(代办验车、代办理赔)
    private String engineno;
    
    //购车日期yyyy-mm（保养）
    private String purchasedate;
    
    //行驶里程（保养）
    private String runMails = "0.0";
    
    //车型（保养）
    private String modelCode;
    
    

	public String getLicenseno() {
		return licenseno;
	}

	public void setLicenseno(String licenseno) {
		this.licenseno = licenseno;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getCustserviceID() {
		return custserviceID;
	}

	public void setCustserviceID(String custserviceID) {
		this.custserviceID = custserviceID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getRegistCode() {
		return registCode;
	}

	public void setRegistCode(String registCode) {
		this.registCode = registCode;
	}

	public String getLinkerName() {
		return linkerName;
	}

	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}

	public String getLinkerMobile() {
		return linkerMobile;
	}

	public void setLinkerMobile(String linkerMobile) {
		this.linkerMobile = linkerMobile;
	}

	public String getAppointment() {
		return appointment;
	}

	public void setAppointment(String appointment) {
		this.appointment = appointment;
	}

	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}

	public String getEngineno() {
		return engineno;
	}

	public void setEngineno(String engineno) {
		this.engineno = engineno;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getPurchasedate() {
		return purchasedate;
	}

	public void setPurchasedate(String purchasedate) {
		this.purchasedate = purchasedate;
	}

	public String getRunMails() {
		return runMails;
	}

	public void setRunMails(String runMails) {
		this.runMails = runMails;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    
    
    
}
