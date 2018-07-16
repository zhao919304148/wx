package com.wap.trans.entity.tr_1041;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BASE_PART")
public class ResTrans1041BasePartEntity {

	@XStreamAlias("WASHCARCODE")
	private String washCarCode; //洗车码

	public String getWashCarCode() {
		return washCarCode;
	}

	public void setWashCarCode(String washCarCode) {
		this.washCarCode = washCarCode;
	}   
	
}
