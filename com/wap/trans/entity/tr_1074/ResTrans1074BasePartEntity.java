package com.wap.trans.entity.tr_1074;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ResTrans1074BasePartEntity {
	
	@XStreamAlias("ORDERID")
	private String orderId ;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
