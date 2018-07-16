package com.wap.trans.entity.tr_1057;
/**
 * 
		
		* 描述:维修保养代金劵多次消费请求信息实体
		*
		* @author 赵硕
		* @version 1.0
		* @since 2017年12月12日 下午1:47:09
 */
public class ReqTrans1057Entity {
   private String licenseNo;//车牌号
   private String consumeAmount;//消费金额
   private String consumeTime;//消费时间
   private String networkName;//网点名称
   private String networkJobid;//网点工号
   private String networkPhone;//网点手机号
   private String networkAddress;//网点地址
   private String fwkCode;//服务卡代码
   private String openId;//用户代码

   
   
public String getLicenseNo() {
	return licenseNo;
}
public void setLicenseNo(String licenseNo) {
	this.licenseNo = licenseNo;
}
public String getConsumeAmount() {
	return consumeAmount;
}
public void setConsumeAmount(String consumeAmount) {
	this.consumeAmount = consumeAmount;
}
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
public String getFwkCode() {
	return fwkCode;
}
public void setFwkCode(String fwkCode) {
	this.fwkCode = fwkCode;
}
public String getOpenId() {
	return openId;
}
public void setOpenId(String openId) {
	this.openId = openId;
}
}
