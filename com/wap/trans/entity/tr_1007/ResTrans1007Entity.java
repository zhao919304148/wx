package com.wap.trans.entity.tr_1007;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1007Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1007BodyEntity resTrans1007BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1007BodyEntity getResTrans1007BodyEntity() {
		return resTrans1007BodyEntity;
	}

	public void setResTrans1007BodyEntity(
			ResTrans1007BodyEntity resTrans1007BodyEntity) {
		this.resTrans1007BodyEntity = resTrans1007BodyEntity;
	}

}
