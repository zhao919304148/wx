package com.wap.trans.entity.tr_1008;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1008Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1008BodyEntity resTrans1008BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1008BodyEntity getResTrans1008BodyEntity() {
		return resTrans1008BodyEntity;
	}

	public void setResTrans1008BodyEntity(
			ResTrans1008BodyEntity resTrans1008BodyEntity) {
		this.resTrans1008BodyEntity = resTrans1008BodyEntity;
	}

}
