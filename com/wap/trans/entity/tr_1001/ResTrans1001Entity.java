package com.wap.trans.entity.tr_1001;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1001Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1001BodyEntity resTrans1001BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1001BodyEntity getResTrans1001BodyEntity() {
		return resTrans1001BodyEntity;
	}

	public void setResTrans1001BodyEntity(
			ResTrans1001BodyEntity resTrans1001BodyEntity) {
		this.resTrans1001BodyEntity = resTrans1001BodyEntity;
	}

}
