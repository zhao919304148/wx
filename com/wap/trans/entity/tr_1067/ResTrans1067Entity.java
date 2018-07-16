package com.wap.trans.entity.tr_1067;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1067Entity {
	
	@XStreamAlias("HEAD")
	private ResponseHeadEntity head;
	@XStreamAlias("BODY")
	private ResTrans1067BodyEntity body;
	public ResponseHeadEntity getHead() {
		return head;
	}
	public ResTrans1067BodyEntity getBody() {
		return body;
	}
	public void setHead(ResponseHeadEntity head) {
		this.head = head;
	}
	public void setBody(ResTrans1067BodyEntity body) {
		this.body = body;
	}
	
	

}
