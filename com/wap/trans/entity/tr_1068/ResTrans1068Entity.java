package com.wap.trans.entity.tr_1068;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;
@XStreamAlias("PACKET")
public class ResTrans1068Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}
	
}
