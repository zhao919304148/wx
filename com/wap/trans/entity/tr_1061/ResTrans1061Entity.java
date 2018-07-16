package com.wap.trans.entity.tr_1061;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1061Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	
	@XStreamAlias("BODY")
	private ResTrans1061BodyEntity resTrans1061BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1061BodyEntity getResTrans1061BodyEntity() {
		return resTrans1061BodyEntity;
	}

	public void setResTrans1061BodyEntity(
			ResTrans1061BodyEntity resTrans1061BodyEntity) {
		this.resTrans1061BodyEntity = resTrans1061BodyEntity;
	}
	
	
}
