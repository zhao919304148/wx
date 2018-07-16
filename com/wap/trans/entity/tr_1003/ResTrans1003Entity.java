package com.wap.trans.entity.tr_1003;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1003Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1003BodyEntity resTrans1003BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1003BodyEntity getResTrans1003BodyEntity() {
		return resTrans1003BodyEntity;
	}

	public void setResTrans1003BodyEntity(
			ResTrans1003BodyEntity resTrans1003BodyEntity) {
		this.resTrans1003BodyEntity = resTrans1003BodyEntity;
	}

}
