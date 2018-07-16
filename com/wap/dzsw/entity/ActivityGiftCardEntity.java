package com.wap.dzsw.entity;

import java.util.Date;


public class ActivityGiftCardEntity {
	
	private String openid;
	
	
	private String cardtypename;
	
	
	private String validdate;
	
	
	private String cardno;
	
	
	private String cardpass;
	
	private String cardtype;


	public String getCardtype() {
		return cardtype;
	}


	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}


	public String getOpenid() {
		return openid;
	}


	public void setOpenid(String openid) {
		this.openid = openid;
	}


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


	@Override
	public String toString() {
		return "ActivityGiftCardEntity [openid=" + openid + ", cardtypename="
				+ cardtypename + ", validdate=" + validdate + ", cardno="
				+ cardno + ", cardpass=" + cardpass + "]";
	}
	
	
}
