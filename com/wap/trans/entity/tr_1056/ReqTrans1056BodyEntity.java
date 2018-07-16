package com.wap.trans.entity.tr_1056;

public class ReqTrans1056BodyEntity {
	
	//渠道类型(必填) 1-微信
	private String channeltype;		
	
	//渠道唯一标识(非必填)
	private String channelno;		

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
