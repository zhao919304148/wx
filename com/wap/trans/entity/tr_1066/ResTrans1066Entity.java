package com.wap.trans.entity.tr_1066;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1066Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity head;
	@XStreamAlias("BODY")
	private ResTrans1066BodyEntity body;
	public ResponseHeadEntity getHead() {
		return head;
	}
	public void setHead(ResponseHeadEntity head) {
		this.head = head;
	}
	public ResTrans1066BodyEntity getBody() {
		return body;
	}
	public void setBody(ResTrans1066BodyEntity body) {
		this.body = body;
	}
}
