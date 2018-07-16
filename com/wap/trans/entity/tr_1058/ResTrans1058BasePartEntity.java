package com.wap.trans.entity.tr_1058;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ResTrans1058BasePartEntity {

@XStreamAlias("REMAINAMOUNT")
 private String remainAmount;//维修保养代金劵余额

public String getRemainAmount() {
	return remainAmount;
}

public void setRemainAmount(String remainAmount) {
	this.remainAmount = remainAmount;
}


}
