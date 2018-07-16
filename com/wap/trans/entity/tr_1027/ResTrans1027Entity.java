package com.wap.trans.entity.tr_1027;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1027Entity {

	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	
	@XStreamAlias("BODY")
	private ResTrans1027BodyEntity  resTrans1027BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1027BodyEntity getResTrans1027BodyEntity() {
		return resTrans1027BodyEntity;
	}

	public void setResTrans1027BodyEntity(ResTrans1027BodyEntity resTrans1027BodyEntity) {
		this.resTrans1027BodyEntity = resTrans1027BodyEntity;
	}
	
	
}
