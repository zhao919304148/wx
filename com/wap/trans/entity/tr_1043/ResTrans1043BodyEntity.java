package com.wap.trans.entity.tr_1043;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ResTrans1043BodyEntity {
	
	@XStreamAlias("BASEPART")
	private ResTrans1043BasePartEntity basePartEntity;

	public ResTrans1043BasePartEntity getBasePartEntity() {
		return basePartEntity;
	}

	public void setBasePartEntity(ResTrans1043BasePartEntity basePartEntity) {
		this.basePartEntity = basePartEntity;
	}
}
