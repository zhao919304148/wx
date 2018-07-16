package com.wap.trans.entity.tr_1004;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1004Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1004BodyEntity resTrans1004BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1004BodyEntity getResTrans1004BodyEntity() {
		return resTrans1004BodyEntity;
	}

	public void setResTrans1004BodyEntity(
			ResTrans1004BodyEntity resTrans1004BodyEntity) {
		this.resTrans1004BodyEntity = resTrans1004BodyEntity;
	}

}
