package com.wap.trans.entity.tr_1075;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1075Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	
	@XStreamAlias("BODY")
	private ResTrans1075BodyEntity resTrans1074BodyEntity;
	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1075BodyEntity getResTrans1074BodyEntity() {
		return resTrans1074BodyEntity;
	}

	public void setResTrans1074BodyEntity(
			ResTrans1075BodyEntity resTrans1074BodyEntity) {
		this.resTrans1074BodyEntity = resTrans1074BodyEntity;
	}
	
}
