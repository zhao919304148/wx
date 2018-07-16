package com.wap.trans.entity.tr_1066;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ResTrans1066BasePartEntity {
	@XStreamAlias("RESMSG")
	private String resMsg;

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
}
