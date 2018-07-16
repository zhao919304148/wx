package com.wap.wx_interface.entity.custom;

/**
 * 
 * 
 * 描述:电子码注册Vo
 * 
 * @author 赵硕
 * @version 1.0
 * @since 2018年1月30日 下午3:01:34
 */
public class DzmRegistVo {
	private String userCode;// 服务商代码
	private String loginPwd;// 服务商登录密码
	private String isRealCard;// 是否为实体卡“ true”:是，否则为否；可不填默认否
	private String cardType;// 卡类型
	private String cardNo;// 卡号
	private String cardPwd;// 卡密
	private String licenseNo;// 车牌号
	private String phoneNumber;// 手机号，指定卡类型需要自此段

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getIsRealCard() {
		return isRealCard;
	}

	public void setIsRealCard(String isRealCard) {
		this.isRealCard = isRealCard;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardPwd() {
		return cardPwd;
	}

	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
