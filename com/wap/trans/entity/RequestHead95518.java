package com.wap.trans.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class RequestHead95518 {
	@XStreamAlias("request_type")
	private String requestType;//交易类型
	
	@XStreamAlias("uuid")
	private String uuid;//唯一标志
	
	@XStreamAlias("user")
	private String user;//用户代码
	
	@XStreamAlias("password")
	private String password;//发送者用户密码
	
	@XStreamAlias("server_version")
	private String serverVersion;//服务提供方接口版本号
	
	@XStreamAlias("sender")
	private String sender;//发送者用户代码

	@XStreamAlias("flowintime")
	private String flowintime;//发送时间 (yyyy-MM-dd HH:mm:ss)

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getServerVersion() {
		return serverVersion;
	}

	public void setServerVersion(String serverVersion) {
		this.serverVersion = serverVersion;
	}

	public String getFlowintime() {
		return flowintime;
	}

	public void setFlowintime(String flowintime) {
		this.flowintime = flowintime;
	}
}
