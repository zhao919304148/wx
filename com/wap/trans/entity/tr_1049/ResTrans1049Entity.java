package com.wap.trans.entity.tr_1049;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1049Entity {
	
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	
	@XStreamAlias("BODY")
	private ResTrans1049BodyEntity resTrans1049BodyEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	public ResTrans1049BodyEntity getResTrans1049BodyEntity() {
		return resTrans1049BodyEntity;
	}

	public void setResTrans1049BodyEntity(
			ResTrans1049BodyEntity resTrans1049BodyEntity) {
		this.resTrans1049BodyEntity = resTrans1049BodyEntity;
	}

}
