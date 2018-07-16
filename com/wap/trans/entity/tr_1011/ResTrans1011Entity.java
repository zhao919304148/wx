package com.wap.trans.entity.tr_1011;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1011Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1011BodyEntity resTrans1011BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1011BodyEntity getResTrans1011BodyEntity() {
		return resTrans1011BodyEntity;
	}

	public void setResTrans1011BodyEntity(
			ResTrans1011BodyEntity resTrans1011BodyEntity) {
		this.resTrans1011BodyEntity = resTrans1011BodyEntity;
	}

}
