package com.wap.trans.entity.tr_1052;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1052Entity {
	
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	
	@XStreamAlias("BODY")
	private ResTrans1052BodyEntity resTrans1052BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1052BodyEntity getResTrans1052BodyEntity() {
		return resTrans1052BodyEntity;
	}

	public void setResTrans1052BodyEntity(
			ResTrans1052BodyEntity resTrans1052BodyEntity) {
		this.resTrans1052BodyEntity = resTrans1052BodyEntity;
	}
}
