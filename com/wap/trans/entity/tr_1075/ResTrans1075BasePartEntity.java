package com.wap.trans.entity.tr_1075;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ResTrans1075BasePartEntity {
	
	@XStreamAlias("ORDERID")
	private String orderId ;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
