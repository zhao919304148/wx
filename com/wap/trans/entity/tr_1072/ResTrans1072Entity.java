package com.wap.trans.entity.tr_1072;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1072Entity {
	
	@XStreamAlias("HEAD")
	private ResponseHeadEntity responseHeadEntity;
	
	@XStreamAlias("BODY")
	private ResTrans1072BodyEntity resTrans1072BodyEntity;

	public ResponseHeadEntity getResponseHeadEntity() {
		return responseHeadEntity;
	}

	public void setResponseHeadEntity(ResponseHeadEntity responseHeadEntity) {
		this.responseHeadEntity = responseHeadEntity;
	}

	public ResTrans1072BodyEntity getResTrans1072BodyEntity() {
		return resTrans1072BodyEntity;
	}

	public void setResTrans1072BodyEntity(
			ResTrans1072BodyEntity resTrans1072BodyEntity) {
		this.resTrans1072BodyEntity = resTrans1072BodyEntity;
	}

}
