package com.wap.trans.entity.tr_1046;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1046Entity {
	
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	
	@XStreamAlias("BODY")
	private ResTrans1046BodyEntity   resTrans1046BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1046BodyEntity getResTrans1046BodyEntity() {
		return resTrans1046BodyEntity;
	}

	public void setResTrans1046BodyEntity(
			ResTrans1046BodyEntity resTrans1046BodyEntity) {
		this.resTrans1046BodyEntity = resTrans1046BodyEntity;
	}

}
