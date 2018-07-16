package com.wap.trans.entity.tr_1001;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("USER_INFO")
public class ResTrans1001UserInfoEntity {
	@XStreamAlias("USERCODE")
	private String usercode;

	@XStreamAlias("USERNAME")
	private String username;

	@XStreamAlias("PASSWDEXPIREDATE")
	private String passwdexpiredate;

	@XStreamAlias("COMCODE")
	private String comcode;
	
	@XStreamAlias("COMNAME")
	private String comname;

	@XStreamAlias("VALIDSTATUS")
	private String validstatus;

	@XStreamAlias("USERTYPE")
	private String usertype;

	@XStreamAlias("FLAG")
	private String flag;

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswdexpiredate() {
		return passwdexpiredate;
	}

	public void setPasswdexpiredate(String passwdexpiredate) {
		this.passwdexpiredate = passwdexpiredate;
	}

	public String getComcode() {
		return comcode;
	}

	public void setComcode(String comcode) {
		this.comcode = comcode;
	}

	public String getValidstatus() {
		return validstatus;
	}

	public void setValidstatus(String validstatus) {
		this.validstatus = validstatus;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getComname() {
		return comname;
	}

	public void setComname(String comname) {
		this.comname = comname;
	}

}
