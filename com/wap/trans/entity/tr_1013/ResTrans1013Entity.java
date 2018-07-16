package com.wap.trans.entity.tr_1013;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1013Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1013BodyEntity resTrans1013BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1013BodyEntity getResTrans1013BodyEntity() {
		return resTrans1013BodyEntity;
	}

	public void setResTrans1013BodyEntity(
			ResTrans1013BodyEntity resTrans1013BodyEntity) {
		this.resTrans1013BodyEntity = resTrans1013BodyEntity;
	}

}
