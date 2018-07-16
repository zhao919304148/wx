package com.wap.trans.entity.tr_1058;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1058Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	
	@XStreamAlias("BODY")
	private ResTrans1058BodyEntity resTrans1058BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1058BodyEntity getResTrans1058BodyEntity() {
		return resTrans1058BodyEntity;
	}

	public void setResTrans1058BodyEntity(
			ResTrans1058BodyEntity resTrans1058BodyEntity) {
		this.resTrans1058BodyEntity = resTrans1058BodyEntity;
	}
	
	
}
