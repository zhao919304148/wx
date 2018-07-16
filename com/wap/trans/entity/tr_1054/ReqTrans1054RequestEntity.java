package com.wap.trans.entity.tr_1054;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.RequestHead95518;
@XStreamAlias("RequestVo")
public class ReqTrans1054RequestEntity {
	
	@XStreamAlias("requesthead")
	private RequestHead95518 head;
	
	@XStreamAlias("requestBodyVo")
	private  ReqTrans1054BodyEntity  body;
	
	public RequestHead95518 getHead() {
		return head;
	}
	public void setHead(RequestHead95518 head) {
		this.head = head;
	}
	public ReqTrans1054BodyEntity getBody() {
		return body;
	}
	public void setBody(ReqTrans1054BodyEntity body) {
		this.body = body;
	}
}
