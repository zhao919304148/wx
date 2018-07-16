package com.wap.main.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
		
		* 描述:登陆操作记录表
		*
		* @author ZN
		* @version 1.0
		* @since 2015-11-25 上午10:26:39
 */
@Entity
@Table(name = "s_rbac_loginreport")
public class LoginReportEntity {
	@Id
	@Column(name="cid",length = 30)
	private String cid;					//主键
	
	@Column(name = "usercode", length = 20)
	private String usercode;			//用户代码
	
	@Column(name = "password", length = 20)
	private String password;			//用户 密码
	
	@Column(name = "openid", length = 20)
	private String openid;
	
	@Column(name = "comcode", length = 20)
	private String comcode;				//归属机构代码
	
	@Column(name = "logintime",length = 20)
	private Date logintime;				//登入时间
	
	@Column(name = "logouttime",length = 20)
	private Date logouttime;			//登出时间
	
	@Column(name = "validflag",length = 20)
	private String validflag;			//是否注销(0:已注销  1:未注销)
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
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
	public String getComcode() {
		return comcode;
	}
	public void setComcode(String comcode) {
		this.comcode = comcode;
	}
	public Date getLogintime() {
		return logintime;
	}
	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}
	public Date getLogouttime() {
		return logouttime;
	}
	public void setLogouttime(Date logouttime) {
		this.logouttime = logouttime;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getValidflag() {
		return validflag;
	}
	public void setValidflag(String validflag) {
		this.validflag = validflag;
	}
	
}
