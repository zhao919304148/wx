package com.wap.trans.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("HEAD")
public class ResponseHeadEntity {
	/*消息传输类型*/
	@XStreamAlias("TRANS_TYPE")
	private String trans_type;
	/*返回类型代码 1：成功  0：失败*/
	@XStreamAlias("RESPONSE_CODE")
	private String response_code="";
	/*返回信息*/
	@XStreamAlias("RESPONSE_MESSAGE")
	private String response_message;
	/*交易流水号*/
	@XStreamAlias("SERIALNO")
	private String serialno;
	public String getTrans_type() {
		return trans_type;
	}
	public void setTrans_type(String transType) {
		trans_type = transType;
	}
	public String getResponse_code() {
		return response_code;
	}
	public void setResponse_code(String responseCode) {
		response_code = responseCode;
	}
	public String getSerialno() {
		return serialno;
	}
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	public String getResponse_message() {
		return response_message;
	}
	public void setResponse_message(String response_message) {
		this.response_message = response_message;
	}
	
}
