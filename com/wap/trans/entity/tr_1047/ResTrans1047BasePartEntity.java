package com.wap.trans.entity.tr_1047;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ResTrans1047BasePartEntity {

	@XStreamAlias("ENGINENO")
	private String engineno;
	
	@XStreamAlias("FRAMENO")
	private String frameno;
	
	@XStreamAlias("BRANDNAME")
	private String brandname;
	
	@XStreamAlias("CUSTOMERNAME")
	private String customername;
	
	@XStreamAlias("IDENTITYNO")
	private String identityno;
	
	@XStreamAlias("PHONENUMBER")
	private String phonenumber;
	
	@XStreamAlias("COMCODE")
	private String comcode;

	public String getEngineno() {
		return engineno;
	}

	public void setEngineno(String engineno) {
		this.engineno = engineno;
	}

	public String getFrameno() {
		return frameno;
	}

	public void setFrameno(String frameno) {
		this.frameno = frameno;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getIdentityno() {
		return identityno;
	}

	public void setIdentityno(String identityno) {
		this.identityno = identityno;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getComcode() {
		return comcode;
	}

	public void setComcode(String comcode) {
		this.comcode = comcode;
	}
}
