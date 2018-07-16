package com.wap.trans.entity.tr_1059;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BASE_PART")
public class ResTrans1059BasePartEntity {
	
	@XStreamAlias("MONTH")
	private String month;
	
	@XStreamAlias("GOODSSEQNUMBER")
	private String goodsSeqNumber;
	
	public String getGoodsSeqNumber() {
		return goodsSeqNumber;
	}

	public void setGoodsSeqNumber(String goodsSeqNumber) {
		this.goodsSeqNumber = goodsSeqNumber;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
}
