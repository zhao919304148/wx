package com.wap.trans.entity.tr_1055;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.RequestHead95518;
@XStreamAlias("RequestVo")
public class ReqTrans1055RequestEntity {
	
	@XStreamAlias("requesthead")
	private RequestHead95518 head;
	
	@XStreamAlias("requestBodyVo")
	private  ReqTrans1055BodyEntity  body;
	
	public RequestHead95518 getHead() {
		return head;
	}
	public void setHead(RequestHead95518 head) {
		this.head = head;
	}
	
	public ReqTrans1055BodyEntity getBody() {
		return body;
	}
	public void setBody(ReqTrans1055BodyEntity body) {
		this.body = body;
	}
}
