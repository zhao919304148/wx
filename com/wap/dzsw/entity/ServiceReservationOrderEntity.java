package com.wap.dzsw.entity;

public class ServiceReservationOrderEntity {

	private String fwType;  //服务类型
	
	private String identifyType;  //证件类型
	
	private String identifyNo;   //证件号
	
	private String phoneNumber;  //手机号
	
	private String licenseNo;   //车牌号
	
	private String cardNo;      //礼品编号
	
	private String fwAddress;   //接车/交车地点
	
	private String fwDate;     // 接车/交车时间
	
	private String networkName;  //网点名称
	
	private String networkPhone;  //网点电话
	
	private String networkAddress; //网点地址
	
	private String networkJobid;   //网点工号
	
	private String parentNetworkJobid;   //上级网点工号
	
	private String networkId;  //网点id
	
	private String sendType;	//推送类型：保存、修改

	private String openId;	//openid
	
	private String recvmsgPhone;//网点短信接收人

	private String cardType;
	
	public String getFwType() {
		return fwType;
	}

	public void setFwType(String fwType) {
		this.fwType = fwType;
	}

	public String getIdentifyType() {
		return identifyType;
	}

	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	public String getIdentifyNo() {
		return identifyNo;
	}

	public void setIdentifyNo(String identifyNo) {
		this.identifyNo = identifyNo;
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

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
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

	public String getNetworkJobid() {
		return networkJobid;
	}

	public void setNetworkJobid(String networkJobid) {
		this.networkJobid = networkJobid;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getParentNetworkJobid() {
		return parentNetworkJobid;
	}

	public void setParentNetworkJobid(String parentNetworkJobid) {
		this.parentNetworkJobid = parentNetworkJobid;
	}

	public String getRecvmsgPhone() {
		return recvmsgPhone;
	}

	public void setRecvmsgPhone(String recvmsgPhone) {
		this.recvmsgPhone = recvmsgPhone;
	}

	@Override
	public String toString() {
		return "ServiceReservationOrderEntity [fwType=" + fwType + ", identifyType=" + identifyType + ", identifyNo="
				+ identifyNo + ", phoneNumber=" + phoneNumber + ", licenseNo=" + licenseNo + ", cardNo=" + cardNo
				+ ", fwAddress=" + fwAddress + ", fwDate=" + fwDate + ", networkName=" + networkName + ", networkPhone="
				+ networkPhone + ", networkAddress=" + networkAddress + ", networkJobid=" + networkJobid
				+ ", parentNetworkJobid=" + parentNetworkJobid + ", networkId=" + networkId + ", sendType=" + sendType
				+ ", openId=" + openId + ", recvmsgPhone=" + recvmsgPhone + "]";
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	
}
