package com.wap.trans.entity.tr_1043;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1043Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resHeadEntity;
	
	@XStreamAlias("BODY")
	private ResTrans1043BodyEntity bodyEntity;

	public ResponseHeadEntity getResHeadEntity() {
		return resHeadEntity;
	}

	public void setResHeadEntity(ResponseHeadEntity resHeadEntity) {
		this.resHeadEntity = resHeadEntity;
	}

	public ResTrans1043BodyEntity getBodyEntity() {
		return bodyEntity;
	}

	public void setBodyEntity(ResTrans1043BodyEntity bodyEntity) {
		this.bodyEntity = bodyEntity;
	}
	
}
