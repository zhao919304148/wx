package com.wap.trans.entity.tr_1015;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1015Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1015BodyEntity resTrans1015BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1015BodyEntity getResTrans1015BodyEntity() {
		return resTrans1015BodyEntity;
	}

	public void setResTrans1015BodyEntity(
			ResTrans1015BodyEntity resTrans1015BodyEntity) {
		this.resTrans1015BodyEntity = resTrans1015BodyEntity;
	}

}
