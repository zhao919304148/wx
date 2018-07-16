package com.wap.trans.entity.tr_1027;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1027BodyEntity {

	@XStreamAlias("BASE_PART")
	private ResTrans1027BasePartEntity resTrans1027BasePartEntity;

	public ResTrans1027BasePartEntity getResTrans1027BasePartEntity() {
		return resTrans1027BasePartEntity;
	}

	public void setResTrans1027BasePartEntity(ResTrans1027BasePartEntity resTrans1027BasePartEntity) {
		this.resTrans1027BasePartEntity = resTrans1027BasePartEntity;
	}
	
	
	
}
