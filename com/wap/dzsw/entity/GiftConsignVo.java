package com.wap.dzsw.entity;
/**
 * 
		
		* 描述:礼品寄卖Vo
		*
		* @author 赵硕
		* @version 1.0
		* @since 2018年6月6日 下午2:32:12
 */
public class GiftConsignVo {
	private String promotionNumber;//礼品序列号
	
	private String openId;//微信端用户标识
	
	private String cardId;//卡号
	
	private String cardPass;//密码
	
	private String giftName;//礼品名称
	
	private String giftType;//礼品类型
	
	private String consignPrice;//寄卖金额

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

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardPass() {
		return cardPass;
	}

	public void setCardPass(String cardPass) {
		this.cardPass = cardPass;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public String getGiftType() {
		return giftType;
	}

	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}

	public String getConsignPrice() {
		return consignPrice;
	}

	public void setConsignPrice(String consignPrice) {
		this.consignPrice = consignPrice;
	}
	
	
}
