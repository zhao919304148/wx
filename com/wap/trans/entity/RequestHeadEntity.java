package com.wap.trans.entity;

public class RequestHeadEntity {
	/*消息传输类型*/
	private String trans_type;
	/*用户名*/
	private String trans_user;
	/*密码*/
	private String trans_pwd;
	/*异步交易识别号*/
	private String serialno="";
	
	public String getSerialno() {
		return serialno;
	}
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	public String getTrans_type() {
		return trans_type;
	}
	public void setTrans_type(String transType) {
		trans_type = transType;
	}
	public String getTrans_user() {
		return trans_user;
	}
	public void setTrans_user(String trans_user) {
		this.trans_user = trans_user;
	}
	public String getTrans_pwd() {
		return trans_pwd;
	}
	public void setTrans_pwd(String trans_pwd) {
		this.trans_pwd = trans_pwd;
	}
		
}
