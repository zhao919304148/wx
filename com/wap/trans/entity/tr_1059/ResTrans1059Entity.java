package com.wap.trans.entity.tr_1059;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;
@XStreamAlias("PACKET")
public class ResTrans1059Entity {
	
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	
	@XStreamAlias("BODY")
	private ResTrans1059BodyEntity   resTrans1059BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1059BodyEntity getResTrans1059BodyEntity() {
		return resTrans1059BodyEntity;
	}

	public void setResTrans1059BodyEntity(
			ResTrans1059BodyEntity resTrans1059BodyEntity) {
		this.resTrans1059BodyEntity = resTrans1059BodyEntity;
	}
	
}
