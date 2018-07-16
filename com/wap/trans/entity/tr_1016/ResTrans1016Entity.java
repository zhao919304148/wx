package com.wap.trans.entity.tr_1016;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1016Entity {
	
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1016BodyEntity resTrans1016BodyEntity;
	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}
	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}
	public ResTrans1016BodyEntity getResTrans1016BodyEntity() {
		return resTrans1016BodyEntity;
	}
	public void setResTrans1016BodyEntity(
			ResTrans1016BodyEntity resTrans1016BodyEntity) {
		this.resTrans1016BodyEntity = resTrans1016BodyEntity;
	}
}
