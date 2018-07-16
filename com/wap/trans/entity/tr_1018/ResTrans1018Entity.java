package com.wap.trans.entity.tr_1018;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1018Entity {
	
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1018BodyEntity resTrans1018BodyEntity;
	
	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}
	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}
	public ResTrans1018BodyEntity getResTrans1018BodyEntity() {
		return resTrans1018BodyEntity;
	}
	public void setResTrans1018BodyEntity(
			ResTrans1018BodyEntity resTrans1018BodyEntity) {
		this.resTrans1018BodyEntity = resTrans1018BodyEntity;
	}
}
