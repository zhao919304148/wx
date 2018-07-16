package com.wap.trans.entity.tr_1056;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.RequestHead95518;
import com.wap.trans.entity.tr_1056.ReqTrans1056BodyEntity;

@XStreamAlias("RequestVo")
public class ReqTrans1056RequestEntity {
	
	@XStreamAlias("requesthead")
	private RequestHead95518 head;
	
	@XStreamAlias("requestBodyVo")
	private  ReqTrans1056BodyEntity  body;
	
	public RequestHead95518 getHead() {
		return head;
	}
	public void setHead(RequestHead95518 head) {
		this.head = head;
	}
	
	public ReqTrans1056BodyEntity getBody() {
		return body;
	}
	public void setBody(ReqTrans1056BodyEntity body) {
		this.body = body;
	}
	
}
