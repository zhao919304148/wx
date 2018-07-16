package com.wap.trans.entity.tr_1070;
/**
* 
* 描述:1070礼品分享
* @author 赵硕
* @version 1.0
* @since 2018年4月12日 上午10:57:49
 */
public class ReqTrans1070Entity {
   private String promotionNumber;//礼品序列号
   private String openId;//
   private String licenseNo;
   private String  giftSourceFlag;//礼品来源
   

public String getGiftSourceFlag() {
	return giftSourceFlag;
}

public void setGiftSourceFlag(String giftSourceFlag) {
	this.giftSourceFlag = giftSourceFlag;
}

public String getPromotionNumber() {
	return promotionNumber;
}

public void setPromotionNumber(String promotionNumber) {
	this.promotionNumber = promotionNumber;
}

public String getOpenId() {
	return openId;
}

public void setOpenId(String openId) {
	this.openId = openId;
}

public String getLicenseNo() {
	return licenseNo;
}

public void setLicenseNo(String licenseNo) {
	this.licenseNo = licenseNo;
}

}
