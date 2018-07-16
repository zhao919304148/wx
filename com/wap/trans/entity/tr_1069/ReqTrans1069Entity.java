package com.wap.trans.entity.tr_1069;
/**
 * 
 * 描述:1069领取礼品分享 
 * @author 赵硕
 * @version 1.0
 * @since 2018年4月12日 上午10:17:26
 */
public class ReqTrans1069Entity {
	private String id;//轨迹表ID
	private String licenseNo;//车牌号
	private String identifyType;//证件类型
	private String identifyNo;//证件号
	private String phoneNumber;//手机号
	private String friendName;//领取人微信名称
	private String openId;
	private String promotionNumber;//礼品序列号
	private String valiDateCode;//验证码
	private String shareOpenId;//分享人openid
	private String  giftSourceFlag;//礼品来源
	
	public String getGiftSourceFlag() {
		return giftSourceFlag;
	}
	public void setGiftSourceFlag(String giftSourceFlag) {
		this.giftSourceFlag = giftSourceFlag;
	}
	public String getShareOpenId() {
		return shareOpenId;
	}
	public void setShareOpenId(String shareOpenId) {
		this.shareOpenId = shareOpenId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
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
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getPromotionNumber() {
		return promotionNumber;
	}
	public void setPromotionNumber(String promotionNumber) {
		this.promotionNumber = promotionNumber;
	}
	public String getValiDateCode() {
		return valiDateCode;
	}
	public void setValiDateCode(String valiDateCode) {
		this.valiDateCode = valiDateCode;
	}
}
