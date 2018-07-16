package com.wap.wx_interface.entity.custom;

/***
 * 
		
		* 描述:
		*	全年保养生成预约订单请求VO
		* @author 许宝众
		* @version 1.0
		* @since 2017年12月14日 下午4:11:58
 */
public class MaintainOrderRequest {
	private String userId;//	用户id	字符串	Y	微信openId加密后
	private String cardId;//	卡号	字符串	Y	加密卡号
	private String cardPwd;//	卡密	字符串	Y	
	private String maintainType;//	保养方式	字符串	Y	0：上门保养  1：到店保养
	private String licenseNo;//	服务车牌号	字符串	Y	
	private String userMobile;//	客户手机号	字符串	Y	预约手机号
	private String serviceDate;//	服务日期	日期	Y	格式：yyyy-MM-dd
	private String networkName;//	网点名称	字符串	N	到店保养时有值
	private String serviceAddress;//	服务地址	字符串	N	上门保养时有值
	private String oilType;//	机油型号	字符串	Y	
	private String machineType;//	机滤型号		Y	
	private String addServices;//	增值服务		N	多值，用英文分号隔开
	private String thirdOrderNo;//第三方订单号  Y
	private String isBackend;// 是否为后端调用   N  字符串“true”:是，否则为否 
	private String isRealCard;// 是否为实体卡 true 为是 否为不是
	private String orderNo;  //落地端订单号
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getCardPwd() {
		return cardPwd;
	}
	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}
	public String getMaintainType() {
		return maintainType;
	}
	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
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
	public String getAddServices() {
		return addServices;
	}
	public void setAddServices(String addServices) {
		this.addServices = addServices;
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
