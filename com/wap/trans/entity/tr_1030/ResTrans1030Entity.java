package com.wap.trans.entity.tr_1030;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;
import com.wap.trans.entity.tr_1029.ScoreOrder;

@XStreamAlias("PACKET")
public class ResTrans1030Entity {

	@XStreamAlias("HEAD")
	private ResponseHeadEntity resHeadEntity;
	
	@XStreamAlias("BODY")
	private ResTrans1030BodyEntity resTrans1030BodyEntity;
	
	public ResTrans1030BodyEntity getResTrans1030BodyEntity() {
		return resTrans1030BodyEntity;
	}

	public void setResTrans1030BodyEntity(ResTrans1030BodyEntity resTrans1030BodyEntity) {
		this.resTrans1030BodyEntity = resTrans1030BodyEntity;
	}

	public ResponseHeadEntity getResHeadEntity() {
		return resHeadEntity;
	}

	public void setResHeadEntity(ResponseHeadEntity resHeadEntity) {
		this.resHeadEntity = resHeadEntity;
	}
	
}
