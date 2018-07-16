package com.wap.trans.entity.tr_1012;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1012Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1012BodyEntity resTrans1012BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1012BodyEntity getResTrans1012BodyEntity() {
		return resTrans1012BodyEntity;
	}

	public void setResTrans1012BodyEntity(
			ResTrans1012BodyEntity resTrans1012BodyEntity) {
		this.resTrans1012BodyEntity = resTrans1012BodyEntity;
	}

}
