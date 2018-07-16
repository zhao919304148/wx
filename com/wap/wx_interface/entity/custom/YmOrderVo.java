package com.wap.wx_interface.entity.custom;

/**
 * 亿美礼品兑换同步卡号状态的订单实体
		
		* 描述:亿美礼品兑换同步卡号状态的订单实体
		*
		* @author 朱久满
		* @version 1.0
		* @since 2017年7月30日 上午10:43:11
 */
public class YmOrderVo {
	/**手机号**/
	private String mobile;
	/**礼品类型**/
	private String giftType;
	/**卡号**/
	private String cardNo;
	/**卡状态**/
	private String cardStatus;
	/**亿美订单号**/
	private String ymOrderNo;
	/**订单金额**/
	private String orderAmount;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getGiftType() {
		return giftType;
	}
	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public String getYmOrderNo() {
		return ymOrderNo;
	}
	public void setYmOrderNo(String ymOrderNo) {
		this.ymOrderNo = ymOrderNo;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	
}
