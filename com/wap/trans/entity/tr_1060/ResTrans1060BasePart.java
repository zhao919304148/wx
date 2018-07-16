package com.wap.trans.entity.tr_1060;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ResTrans1060BasePart {
	@XStreamAlias("ORDERNO")
	private String orderId;//订单号	      ORDERNO	字符串(30)
	@XStreamAlias("CARDTYPE")
	private String cardType;//cardType	礼品编号	字符串	Y	福袋礼品编号
	@XStreamAlias("CARDTYPENAME")
	private String cardTypeName;//cardTypeName	礼品名称	字符串	Y	福袋礼品名称
	@XStreamAlias("CARDID")
	private String cardId;//cardId 卡号（未加密）

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
}
