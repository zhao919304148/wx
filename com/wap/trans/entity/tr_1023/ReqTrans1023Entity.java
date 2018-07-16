package com.wap.trans.entity.tr_1023;

public class ReqTrans1023Entity {

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
	
	private String networkJobId;   //网点工号
	
	private String parentNetworkJobid;   //上级网点工号
	
	private String sendType;	//推送类型：保存、修改

	private String openId;	//openid
	
	private String name;//客户姓名
	/**总公司用户名**/
	private String username;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getNetworkJobId() {
		return networkJobId;
	}

	public void setNetworkJobId(String networkJobId) {
		this.networkJobId = networkJobId;
	}

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

	public String getFwDate() {
		return fwDate;
	}

	public void setFwDate(String fwDate) {
		this.fwDate = fwDate;
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

	public String getFwAddress() {
		return fwAddress;
	}

	public void setFwAddress(String fwAddress) {
		this.fwAddress = fwAddress;
	}

	public String getParentNetworkJobid() {
		return parentNetworkJobid;
	}

	public void setParentNetworkJobid(String parentNetworkJobid) {
		this.parentNetworkJobid = parentNetworkJobid;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "ReqTrans1023Entity [fwType=" + fwType + ", identifyType="
				+ identifyType + ", identifyNo=" + identifyNo
				+ ", phoneNumber=" + phoneNumber + ", licenseNo=" + licenseNo
				+ ", cardNo=" + cardNo + ", fwAddress=" + fwAddress
				+ ", fwDate=" + fwDate + ", networkName=" + networkName
				+ ", networkPhone=" + networkPhone + ", networkAddress="
				+ networkAddress + ", networkJobId=" + networkJobId
				+ ", parentNetworkJobid=" + parentNetworkJobid + ", sendType="
				+ sendType + ", openId=" + openId + ", name=" + name
				+ ", username=" + username + "]";
	}
}
