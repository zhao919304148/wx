package com.wap.trans.entity.tr_1060;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1060Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity head;
	@XStreamAlias("BODY")
	private ResTrans1060BodyEntity body;
	public ResponseHeadEntity getHead() {
		return head;
	}
	public void setHead(ResponseHeadEntity head) {
		this.head = head;
	}
	public ResTrans1060BodyEntity getBody() {
		return body;
	}
	public void setBody(ResTrans1060BodyEntity body) {
		this.body = body;
	}
}
