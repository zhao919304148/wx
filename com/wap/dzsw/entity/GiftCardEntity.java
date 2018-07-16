package com.wap.dzsw.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class GiftCardEntity {

	
	private String cardNo;
	private String cardPass;
	private String cardType;
	private String cardTypeName;
	private String licenseNo;
	private String phoneNumber;
	private String validDate;
	private String cardStatus;
	private String cardTypeNameCondition;
	private String url;
	private String img;
	private String lastConsumeTime;
	private String encodeCardNo;
	private String shareflag; //null未分享 ；1 以分享未领取；2.以分享已领取；3.退回
	private String friendname; //领取人名称
	private String shareTime; //礼品分享时间
	private String giftSourceFlag; ////礼品来源a:电销接口;0:来电送;1:补录;2:分享礼品;
	
	
	public String getGiftSourceFlag() {
		return giftSourceFlag;
	}
	public void setGiftSourceFlag(String giftSourceFlag) {
		this.giftSourceFlag = giftSourceFlag;
	}
	public String getLastConsumeTime() {
		return lastConsumeTime;
	}
	public void setLastConsumeTime(String lastConsumeTime) {
		this.lastConsumeTime = lastConsumeTime;
	}
	/**礼品ID**/
	private String goodsSeqNumber;
	
	/**油卡充值卡发送日期**/
	private String smsSendDate;
	
	/**洗车卡剩余使用次数**/
	private String remainWashCarCount;
	
	/**礼品归属为礼包时，此字段不为空**/
	private String lbgiftcode;
	
	private String remainAmount;//维修保养（代金券）礼品的剩余额度
	
	private String totalAmount;//维修保养（代金券）初始总金额
	
	public String getLbgiftcode() {
		return lbgiftcode;
	}
	public void setLbgiftcode(String lbgiftcode) {
		this.lbgiftcode = lbgiftcode;
	}
	public String getRemainWashCarCount() {
		return remainWashCarCount;
	}
	public void setRemainWashCarCount(String remainWashCarCount) {
		this.remainWashCarCount = remainWashCarCount;
	}
	public String getCardTypeNameCondition() {
		return cardTypeNameCondition;
	}
	public void setCardTypeNameCondition(String cardTypeNameCondition) {
		this.cardTypeNameCondition = cardTypeNameCondition;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardPass() {
		return cardPass;
	}
	public void setCardPass(String cardPass) {
		this.cardPass = cardPass;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardTypeName() {
		return cardTypeName;
	}
	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
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
	public String getValidDate() {
		return validDate;
	}
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getGoodsSeqNumber() {
		return goodsSeqNumber;
	}
	public void setGoodsSeqNumber(String goodsSeqNumber) {
		this.goodsSeqNumber = goodsSeqNumber;
	}
	public String getSmsSendDate() {
		return smsSendDate;
	}
	public void setSmsSendDate(String smsSendDate) {
		this.smsSendDate = smsSendDate;
	}
	public String getRemainAmount() {
		return remainAmount;
	}
	public void setRemainAmount(String remainAmount) {
		this.remainAmount = remainAmount;
	}
	
	public String getEncodeCardNo() {
		return encodeCardNo;
	}
	public void setEncodeCardNo(String encodeCardNo) {
		this.encodeCardNo = encodeCardNo;
	}
	
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public String getShareflag() {
		return shareflag;
	}
	public void setShareflag(String shareflag) {
		this.shareflag = shareflag;
	}
	public String getFriendname() {
		return friendname;
	}
	public void setFriendname(String friendname) {
		this.friendname = friendname;
	}
	public String getShareTime() {
		return shareTime;
	}
	public void setShareTime(String shareTime) {
		this.shareTime = shareTime;
	}
	@Override
	public String toString() {
		return "GiftCardEntity [cardNo=" + cardNo + ", cardPass=" + cardPass
				+ ", cardType=" + cardType + ", cardTypeName=" + cardTypeName
				+ ", licenseNo=" + licenseNo + ", phoneNumber=" + phoneNumber
				+ ", validDate=" + validDate + ", cardStatus=" + cardStatus
				+ ", cardTypeNameCondition=" + cardTypeNameCondition + ", url="
				+ url + ", img=" + img + ", lastConsumeTime=" + lastConsumeTime
				+ ", encodeCardNo=" + encodeCardNo + ", goodsSeqNumber="
				+ goodsSeqNumber + ", smsSendDate=" + smsSendDate
				+ ", remainWashCarCount=" + remainWashCarCount
				+ ", lbgiftcode=" + lbgiftcode + ", remainAmount="
				+ remainAmount + ", totalAmount=" + totalAmount
				+ ", getLastConsumeTime()=" + getLastConsumeTime()
				+ ", getLbgiftcode()=" + getLbgiftcode()
				+ ", getRemainWashCarCount()=" + getRemainWashCarCount()
				+ ", getCardTypeNameCondition()=" + getCardTypeNameCondition()
				+ ", getCardNo()=" + getCardNo() + ", getCardPass()="
				+ getCardPass() + ", getCardType()=" + getCardType()
				+ ", getCardTypeName()=" + getCardTypeName()
				+ ", getLicenseNo()=" + getLicenseNo() + ", getPhoneNumber()="
				+ getPhoneNumber() + ", getValidDate()=" + getValidDate()
				+ ", getCardStatus()=" + getCardStatus() + ", getUrl()="
				+ getUrl() + ", getImg()=" + getImg()
				+ ", getGoodsSeqNumber()=" + getGoodsSeqNumber()
				+ ", getSmsSendDate()=" + getSmsSendDate()
				+ ", getRemainAmount()=" + getRemainAmount()
				+ ", getEncodeCardNo()=" + getEncodeCardNo()
				+ ", getTotalAmount()=" + getTotalAmount() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
}
