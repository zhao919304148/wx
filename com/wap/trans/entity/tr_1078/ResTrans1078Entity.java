package com.wap.trans.entity.tr_1078;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1078Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	
	@XStreamAlias("BODY")
	private ResTrans1078BodyEntity resTrans1074BodyEntity;
	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1078BodyEntity getResTrans1074BodyEntity() {
		return resTrans1074BodyEntity;
	}

	public void setResTrans1074BodyEntity(
			ResTrans1078BodyEntity resTrans1074BodyEntity) {
		this.resTrans1074BodyEntity = resTrans1074BodyEntity;
	}
	
}
