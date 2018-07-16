package com.wap.dzsw.entity;

/**
 * 
 * 描述:跳转亿美寄卖页面数据组装
 * 
 * @author 赵硕
 * @version 1.0
 * @since 2018年6月15日 下午3:15:46
 */
public class YimeiGiftConSignVo {
	private String orderCode;// 订单号
	private String partner;// partner商户
	private String giftCode;// 礼品
	private String giftName;// 礼品名称
	private String giftType;// 礼品类型
	private String customerId;// 用户唯一标识
	private String mobile;// 手机号
	private String count;// 礼物数量
	private String code;// 券码
	private String amount;// 订单金额
	private String discount;// 折扣
	private String openid;//
	private String sign;// 签名

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getGiftCode() {
		return giftCode;
	}

	public void setGiftCode(String giftCode) {
		this.giftCode = giftCode;
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

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "YimeiGiftConSignVo [orderCode=" + orderCode + ", partner="
				+ partner + ", giftCode=" + giftCode + ", giftName=" + giftName
				+ ", giftType=" + giftType + ", customerId=" + customerId
				+ ", mobile=" + mobile + ", count=" + count + ", code=" + code
				+ ", amount=" + amount + ", discount=" + discount + ", openid="
				+ openid + ", sign=" + sign + "]";
	}

}
