package com.wap.trans.entity.tr_1020;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1020Entity {
	
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1020BodyEntity resTrans1020BodyEntity;
	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}
	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}
	public ResTrans1020BodyEntity getResTrans1020BodyEntity() {
		return resTrans1020BodyEntity;
	}
	public void setResTrans1020BodyEntity(
			ResTrans1020BodyEntity resTrans1020BodyEntity) {
		this.resTrans1020BodyEntity = resTrans1020BodyEntity;
	}
}
