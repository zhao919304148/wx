package com.wap.main.entity;

public class UserEntity {
	private String usercode; // 用户代码
	private String password; // 用户密码
	private String username; // 用户名
	private String passwdexpiredate; // 密码过期日期
	private String comcode; // 归属机构代码
	private String comname; // 归属机构名称
	private String validstatus; // 有效状态
	private String usertype; // 用户类型
	private String flag; // 标志位

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
