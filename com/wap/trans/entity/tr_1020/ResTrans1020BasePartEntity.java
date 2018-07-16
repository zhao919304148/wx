package com.wap.trans.entity.tr_1020;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BASE_PART")
public class ResTrans1020BasePartEntity {
	
	@XStreamAlias("RECEIVENUM")
	private String receivenum;// 已领取活动礼品人数

	public String getReceivenum() {
		return receivenum;
	}

	public void setReceivenum(String receivenum) {
		this.receivenum = receivenum;
	}
}
