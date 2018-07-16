package com.wap.trans.entity.tr_1024;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("BODY")
public class ResTrans1024BodyEntity {

	@XStreamAlias("BASE_PART")
	private ResTrans1024BasePartEntity resTrans1024BasePartEntity;

	public ResTrans1024BasePartEntity getResTrans1024BasePartEntity() {
		return resTrans1024BasePartEntity;
	}

	public void setResTrans1024BasePartEntity(ResTrans1024BasePartEntity resTrans1024BasePartEntity) {
		this.resTrans1024BasePartEntity = resTrans1024BasePartEntity;
	}
	
	
	
}
