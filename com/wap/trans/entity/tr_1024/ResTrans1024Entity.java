package com.wap.trans.entity.tr_1024;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1024Entity {

	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	
	@XStreamAlias("BODY")
	private ResTrans1024BodyEntity  resTrans1024BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1024BodyEntity getResTrans1024BodyEntity() {
		return resTrans1024BodyEntity;
	}

	public void setResTrans1024BodyEntity(ResTrans1024BodyEntity resTrans1024BodyEntity) {
		this.resTrans1024BodyEntity = resTrans1024BodyEntity;
	}

	
	
	
	
}
