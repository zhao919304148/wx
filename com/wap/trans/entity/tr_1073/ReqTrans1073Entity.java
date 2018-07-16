package com.wap.trans.entity.tr_1073;
/**
* 
* 描述:1073礼品分享
* @author 赵硕
* @version 1.0
* @since 2018年4月12日 上午10:57:49
 */
public class ReqTrans1073Entity {
   private String id;//轨迹表id
   private String shareStatus;//1：成功  0：失败
   private String promotionNumber;//礼品序列号
   private String licenseNo;//车牌号
   private String giftSourceFlag;//礼品来源标识
   private String openId;//
public String getOpenId() {
	return openId;
}
public void setOpenId(String openId) {
	this.openId = openId;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getShareStatus() {
	return shareStatus;
}
public void setShareStatus(String shareStatus) {
	this.shareStatus = shareStatus;
}
public String getPromotionNumber() {
	return promotionNumber;
}
public void setPromotionNumber(String promotionNumber) {
	this.promotionNumber = promotionNumber;
}
public String getLicenseNo() {
	return licenseNo;
}
public void setLicenseNo(String licenseNo) {
	this.licenseNo = licenseNo;
}
public String getGiftSourceFlag() {
	return giftSourceFlag;
}
public void setGiftSourceFlag(String giftSourceFlag) {
	this.giftSourceFlag = giftSourceFlag;
}

}
