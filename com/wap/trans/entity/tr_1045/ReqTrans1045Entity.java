package com.wap.trans.entity.tr_1045;
/**
 * 
		
* 描述:
*	亿美礼品兑换同步卡号状态
* @author 骆利锋
* @version 1.0
* @since 2017年5月15日 下午5:40:01
 */
public class ReqTrans1045Entity {
	
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

	@Override
	public String toString() {
		return this.mobile + "," + this.giftType + "," + this.cardNo + "," + this.cardStatus + "," + this.ymOrderNo + "," + this.orderAmount;
	}
}
