package com.wap.trans.entity.tr_1029;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1029Entity {

	@XStreamAlias("HEAD")
	private ResponseHeadEntity resHeadEntity;
	
	@XStreamAlias("BODY")
	private ResTrans1029BodyEntity  resTrans1028BodyEntity;

	public ResponseHeadEntity getResHeadEntity() {
		return resHeadEntity;
	}

	public void setResHeadEntity(ResponseHeadEntity resHeadEntity) {
		this.resHeadEntity = resHeadEntity;
	}

	public ResTrans1029BodyEntity getResTrans1028BodyEntity() {
		return resTrans1028BodyEntity;
	}

	public void setResTrans1028BodyEntity(ResTrans1029BodyEntity resTrans1028BodyEntity) {
		this.resTrans1028BodyEntity = resTrans1028BodyEntity;
	}

	
	
	
}
