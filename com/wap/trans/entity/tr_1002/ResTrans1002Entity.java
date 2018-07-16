package com.wap.trans.entity.tr_1002;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1002Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1002BodyEntity resTrans1002BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1002BodyEntity getResTrans1002BodyEntity() {
		return resTrans1002BodyEntity;
	}

	public void setResTrans1002BodyEntity(
			ResTrans1002BodyEntity resTrans1002BodyEntity) {
		this.resTrans1002BodyEntity = resTrans1002BodyEntity;
	}

}
