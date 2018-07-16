package com.wap.trans.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ResponseHead95518 {
	
	@XStreamAlias("uuid")
	private String uuid;//唯一标志
	
	@XStreamAlias("request_type")
	private String requestType;//交易类型
	
	@XStreamAlias("response_code")
	private String response_code;//响应代码
	
	@XStreamAlias("error_message")
	private String error_message;//错误信息
	
	@XStreamAlias("server_version")
	private String serverVersion;//服务提供方接口版本号
	
	@XStreamAlias("sender")
	private String sender;//发送者用户代码

	@XStreamAlias("timestamp")
	private String timestamp;//当前时间，精确到毫秒 (yyyy-MM-dd HH:mm:ss)

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getResponse_code() {
		return response_code;
	}

	public void setResponse_code(String response_code) {
		this.response_code = response_code;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public String getServerVersion() {
		return serverVersion;
	}

	public void setServerVersion(String serverVersion) {
		this.serverVersion = serverVersion;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}
