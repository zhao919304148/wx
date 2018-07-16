package com.wap.trans.entity.tr_1078;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ResTrans1078BasePartEntity {
	
	@XStreamAlias("ORDERID")
	private String orderId ;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
