package com.wap.trans.entity.tr_1033;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ResTrans1033BodyBasePart {
	
	@XStreamAlias("PHONENUMBER")
	private String phoneNumber; //手机号

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
