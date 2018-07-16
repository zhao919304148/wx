package com.wap.trans.entity.tr_1032;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;
import com.wap.trans.entity.tr_1029.ScoreOrder;

@XStreamAlias("PACKET")
public class ResTrans1032Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resHeadEntity;

	public ResponseHeadEntity getResHeadEntity() {
		return resHeadEntity;
	}

	public void setResHeadEntity(ResponseHeadEntity resHeadEntity) {
		this.resHeadEntity = resHeadEntity;
	}
	
}
