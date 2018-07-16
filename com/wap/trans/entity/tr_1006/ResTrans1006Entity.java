package com.wap.trans.entity.tr_1006;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1006Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1006BodyEntity resTrans1006BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1006BodyEntity getResTrans1006BodyEntity() {
		return resTrans1006BodyEntity;
	}

	public void setResTrans1006BodyEntity(
			ResTrans1006BodyEntity resTrans1006BodyEntity) {
		this.resTrans1006BodyEntity = resTrans1006BodyEntity;
	}

}
