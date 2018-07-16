package com.wap.trans.entity.tr_1046;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CARD_DATA")
public class ResTrans1046CardDataEntity {
	
	@XStreamAlias("CARDTYPE")
	private String cardtype;
	
	@XStreamAlias("CARDTYPENAME")
	private String cardtypename;
	
	@XStreamAlias("VALIDDATE")
	private String validdate;
	
	@XStreamAlias("CARDNO")
	private String cardno;
	
	@XStreamAlias("CARDPASS")
	private String cardpass;

	public String getCardtypename() {
		return cardtypename;
	}

	public void setCardtypename(String cardtypename) {
		this.cardtypename = cardtypename;
	}

	public String getValiddate() {
		return validdate;
	}

	public void setValiddate(String validdate) {
		this.validdate = validdate;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getCardpass() {
		return cardpass;
	}

	public void setCardpass(String cardpass) {
		this.cardpass = cardpass;
	}

	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
}
