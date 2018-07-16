package com.wap.trans.entity.tr_1025;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;
import com.wap.trans.entity.tr_1021.ResTrans1021BodyEntity;

@XStreamAlias("PACKET")
public class ResTrans1025Entity {

	@XStreamAlias("HEAD")
	private ResponseHeadEntity resuestHeadEntity;
	

	public ResponseHeadEntity getResuestHeadEntity() {
		return resuestHeadEntity;
	}

	public void setResuestHeadEntity(ResponseHeadEntity resuestHeadEntity) {
		this.resuestHeadEntity = resuestHeadEntity;
	}

	
	
	
	
	
}
