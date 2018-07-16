package com.wap.trans.entity.tr_1017;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1017Entity {
	
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1017BodyEntity resTrans1017BodyEntity;
	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}
	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}
	public ResTrans1017BodyEntity getResTrans1017BodyEntity() {
		return resTrans1017BodyEntity;
	}
	public void setResTrans1017BodyEntity(
			ResTrans1017BodyEntity resTrans1017BodyEntity) {
		this.resTrans1017BodyEntity = resTrans1017BodyEntity;
	}

}
