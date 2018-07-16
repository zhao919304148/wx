package com.wap.trans.entity.tr_1047;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1047Entity {

	@XStreamAlias("HEAD")
	private ResponseHeadEntity responseHeadEntity;
	
	@XStreamAlias("BODY")
	private ResTrans1047BodyEntity resTrans1047BodyEntity;

	public ResponseHeadEntity getResponseHeadEntity() {
		return responseHeadEntity;
	}

	public void setResponseHeadEntity(ResponseHeadEntity responseHeadEntity) {
		this.responseHeadEntity = responseHeadEntity;
	}

	public ResTrans1047BodyEntity getResTrans1047BodyEntity() {
		return resTrans1047BodyEntity;
	}

	public void setResTrans1047BodyEntity(
			ResTrans1047BodyEntity resTrans1047BodyEntity) {
		this.resTrans1047BodyEntity = resTrans1047BodyEntity;
	}
	
}
