package com.wap.trans.entity.tr_1021;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1021Entity {

	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	
	@XStreamAlias("BODY")
	private ResTrans1021BodyEntity   resTrans1021BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1021BodyEntity getResTrans1021BodyEntity() {
		return resTrans1021BodyEntity;
	}

	public void setResTrans1021BodyEntity(ResTrans1021BodyEntity resTrans1021BodyEntity) {
		this.resTrans1021BodyEntity = resTrans1021BodyEntity;
	}
	
	
}
