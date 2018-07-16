package com.wap.trans.entity.tr_1055;

public class ReqTrans1055BodyEntity {
	
	//联系电话
	private String telno;
	//渠道类型  1-微信
	private String channeltype;
	//渠道唯一标识(非必填)
	private String channelno;

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getChanneltype() {
		return channeltype;
	}

	public void setChanneltype(String channeltype) {
		this.channeltype = channeltype;
	}

	public String getChannelno() {
		return channelno;
	}

	public void setChannelno(String channelno) {
		this.channelno = channelno;
	}
	
}
