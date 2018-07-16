package com.wap.trans.entity.tr_1047;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1047BodyEntity {
	
	@XStreamAlias("BASE_PART")
	private ResTrans1047BasePartEntity resTrans1047BasePartEntity;

	public ResTrans1047BasePartEntity getResTrans1047BasePartEntity() {
		return resTrans1047BasePartEntity;
	}

	public void setResTrans1047BasePartEntity(
			ResTrans1047BasePartEntity resTrans1047BasePartEntity) {
		this.resTrans1047BasePartEntity = resTrans1047BasePartEntity;
	}
	
}
