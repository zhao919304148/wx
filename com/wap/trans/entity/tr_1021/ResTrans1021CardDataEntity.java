package com.wap.trans.entity.tr_1021;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CARD_DATA")
public class ResTrans1021CardDataEntity {

	@XStreamAlias("CARDNO")
	private String cardNo;   //礼品ID
	
	@XStreamAlias("CARDPASS")
	private String cardPass;  //礼品密码
	
	@XStreamAlias("CARDTYPE")
	private String cardType;   //服务卡种类
	
	@XStreamAlias("CARDTYPENAME")
	private String cardTypeName;   //服务卡种类名称
	
	@XStreamAlias("LICENSENO")
	private String licenseNo;   //车牌号
	
	@XStreamAlias("PHONENUMBER")
	private String phoneNumber;   //手机号
	
	@XStreamAlias("VALIDDATE")
	private String validDate;    //有效期
	
	@XStreamAlias("CARDSTATUS")
	private String cardStatus;   //使用状态
	@XStreamAlias("GOODSSEQNUMBER")
	private String goodsSeqNumber; 
	@XStreamAlias("SMSSENDDATE")
	private String smsSendDate;
	
	@XStreamAlias("REMAINWASHCARCOUNT")
	private String remainWashCarCount; //洗车卡剩余使用次数
	
	@XStreamAlias("LBGIFTCODE")
	private String lbgiftcode;//礼品归属为礼包时，此字段不为空
	
	@XStreamAlias("LASTCONSUMETIME")
	private String lastConsumeTime;//全年洗车券，上次礼品使用时间
	
	@XStreamAlias("REMAINAMOUNT")
	private String remainAmount;//维修保养（代金券）礼品的剩余额度
	
	@XStreamAlias("TOTALAMOUNT")
	private String totalAmount;//维修保养（代金券）原始总额度
	
	/************************礼品分享后加字段**************/
	@XStreamAlias("SHAREFLAG")
	private String shareflag; //null未分享 ；1 以分享未领取；2.以分享已领取；3.退回
	
	@XStreamAlias("FRIENDNAME")
	private String friendname; //领取人名称
	
	@XStreamAlias("SHARETIME")
	private String shareTime; //礼品分享时间
	
	@XStreamAlias("GIFTSOURCEFLAG")
	private String giftSourceFlag; //礼品来源a:电销接口;0:来电送;1:补录;2:分享礼品;
	
	public String getGiftSourceFlag() {
		return giftSourceFlag;
	}

	public void setGiftSourceFlag(String giftSourceFlag) {
		this.giftSourceFlag = giftSourceFlag;
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

	public String getLbgiftcode() {
		return lbgiftcode;
	}
	
	public String getLastConsumeTime() {
		return lastConsumeTime;
	}

	public void setLastConsumeTime(String lastConsumeTime) {
		this.lastConsumeTime = lastConsumeTime;
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

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
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

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}
