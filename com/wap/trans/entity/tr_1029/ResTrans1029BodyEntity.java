package com.wap.trans.entity.tr_1029;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1029BodyEntity {

	@XStreamAlias("BASE_PART")
	private ResTrans1029BasePartEntity resTrans1029BasePartEntity;

	public ResTrans1029BasePartEntity getResTrans1029BasePartEntity() {
		return resTrans1029BasePartEntity;
	}

	public void setResTrans1029BasePartEntity(
			ResTrans1029BasePartEntity resTrans1029BasePartEntity) {
		this.resTrans1029BasePartEntity = resTrans1029BasePartEntity;
	}

	
}
