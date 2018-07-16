package com.wap.trans.entity.tr_1071;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("PACKET")
public class ResTrans1071Entity {
	
	@XStreamAlias("HEAD")
	private ResponseHeadEntity responseHeadEntity;

	public ResponseHeadEntity getResponseHeadEntity() {
		return responseHeadEntity;
	}

	public void setResponseHeadEntity(ResponseHeadEntity responseHeadEntity) {
		this.responseHeadEntity = responseHeadEntity;
	}
}
