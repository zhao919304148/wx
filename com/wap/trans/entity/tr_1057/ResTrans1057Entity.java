package com.wap.trans.entity.tr_1057;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;
@XStreamAlias("PACKET")
public class ResTrans1057Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	
	@XStreamAlias("BODY")
    private ResTrans1057BodyEntity resTrans1057BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1057BodyEntity getResTrans1057BodyEntity() {
		return resTrans1057BodyEntity;
	}

	public void setResTrans1057BodyEntity(
			ResTrans1057BodyEntity resTrans1057BodyEntity) {
		this.resTrans1057BodyEntity = resTrans1057BodyEntity;
	}
	
	
}
