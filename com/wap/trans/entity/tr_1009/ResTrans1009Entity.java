package com.wap.trans.entity.tr_1009;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1009Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1009BodyEntity resTrans1009BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1009BodyEntity getResTrans1009BodyEntity() {
		return resTrans1009BodyEntity;
	}

	public void setResTrans1009BodyEntity(
			ResTrans1009BodyEntity resTrans1009BodyEntity) {
		this.resTrans1009BodyEntity = resTrans1009BodyEntity;
	}

}
