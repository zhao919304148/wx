package com.wap.dzsw.entity;

public class SaveYuYueData {

	
	private String fwType;//服务类型

	private String identifyType;  //证件类型
	
	private String identifyNo;   //证件号
	
	private String networkJobId;//网点工号
	
	private String parentNetworkJobid;//上级网点工号
	
	private String networkAddress;//网点地址
	
	private String networkName;//网点名称
	
	private String recvmsgPhone;//网点短信接收电话
	
	private String networkPhone;//网点电话
	
	private String licenseNo;//车牌号
	
	private String cardNo;//券号
	
	private String fwDate;//预约日期
	
	private String phoneNumber;//客户电话
	
	private String name;//客户姓名
	
	private String openId;
	
	private String sendTpe;//保存或者修改0:1
	
	private String cardType;//礼品类型

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getFwType() {
		return fwType;
	}

	public void setFwType(String fwType) {
		this.fwType = fwType;
	}

	public String getNetworkJobId() {
		return networkJobId;
	}

	public void setNetworkJobId(String networkJobId) {
		this.networkJobId = networkJobId;
	}

	public String getParentNetworkJobid() {
		return parentNetworkJobid;
	}

	public void setParentNetworkJobid(String parentNetworkJobid) {
		this.parentNetworkJobid = parentNetworkJobid;
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


	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRecvmsgPhone() {
		return recvmsgPhone;
	}

	public void setRecvmsgPhone(String recvmsgPhone) {
		this.recvmsgPhone = recvmsgPhone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNetworkAddress() {
		return networkAddress;
	}

	public void setNetworkAddress(String networkAddress) {
		this.networkAddress = networkAddress;
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


	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
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

	public String getFwDate() {
		return fwDate;
	}

	public void setFwDate(String fwDate) {
		this.fwDate = fwDate;
	}


	public String getSendTpe() {
		return sendTpe;
	}

	public void setSendTpe(String sendTpe) {
		this.sendTpe = sendTpe;
	}
	
	@Override
	public String toString() {
		return "SaveYuYueData [fwType=" + fwType + ", identifyType=" + identifyType + ", identifyNo=" + identifyNo
				+ ", networkJobId=" + networkJobId + ", parentNetworkJobid=" + parentNetworkJobid + ", networkAddress="
				+ networkAddress + ", networkName=" + networkName + ", recvmsgPhone=" + recvmsgPhone + ", networkPhone="
				+ networkPhone + ", licenseNo=" + licenseNo + ", cardNo=" + cardNo + ", fwDate=" + fwDate
				+ ", phoneNumber=" + phoneNumber + ", name=" + name + ", openId=" + openId + ", sendTpe=" + sendTpe
				+ "]";
	}
	
}
