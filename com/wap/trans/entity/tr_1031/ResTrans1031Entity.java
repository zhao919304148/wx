package com.wap.trans.entity.tr_1031;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;
import com.wap.trans.entity.tr_1029.ScoreOrder;

@XStreamAlias("PACKET")
public class ResTrans1031Entity {

	@XStreamAlias("HEAD")
	private ResponseHeadEntity resHeadEntity;
	
	@XStreamAlias("BODY")
	private ResTrans1031BodyEntity resTrans1031BodyEntity;

	public ResponseHeadEntity getResHeadEntity() {
		return resHeadEntity;
	}

	public void setResHeadEntity(ResponseHeadEntity resHeadEntity) {
		this.resHeadEntity = resHeadEntity;
	}

	public ResTrans1031BodyEntity getResTrans1031BodyEntity() {
		return resTrans1031BodyEntity;
	}

	public void setResTrans1031BodyEntity(ResTrans1031BodyEntity resTrans1031BodyEntity) {
		this.resTrans1031BodyEntity = resTrans1031BodyEntity;
	}
	
}
