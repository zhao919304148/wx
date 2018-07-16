package com.wap.trans.entity.tr_1010;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1010Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1010BodyEntity resTrans1010BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1010BodyEntity getResTrans1010BodyEntity() {
		return resTrans1010BodyEntity;
	}

	public void setResTrans1010BodyEntity(
			ResTrans1010BodyEntity resTrans1010BodyEntity) {
		this.resTrans1010BodyEntity = resTrans1010BodyEntity;
	}

}
