package com.wap.trans.entity.tr_1020;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1020BodyEntity {
	
	@XStreamAlias("BASE_PART")
	private ResTrans1020BasePartEntity resTrans1020BasePartEntity;

	public ResTrans1020BasePartEntity getResTrans1020BasePartEntity() {
		return resTrans1020BasePartEntity;
	}

	public void setResTrans1020BasePartEntity(
			ResTrans1020BasePartEntity resTrans1020BasePartEntity) {
		this.resTrans1020BasePartEntity = resTrans1020BasePartEntity;
	}

}
