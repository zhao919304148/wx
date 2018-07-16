package com.wap.trans.entity.tr_1005;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1005Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1005BodyEntity resTrans1005BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1005BodyEntity getResTrans1005BodyEntity() {
		return resTrans1005BodyEntity;
	}

	public void setResTrans1005BodyEntity(
			ResTrans1005BodyEntity resTrans1005BodyEntity) {
		this.resTrans1005BodyEntity = resTrans1005BodyEntity;
	}

}
