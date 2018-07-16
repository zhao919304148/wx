package com.wap.trans.entity.tr_1044;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1044BodyEntity {
	
	@XStreamAlias("BASE_PART")
	private ResTrans1044BasePartEntity   resTrans1044BasePartEntity;

	public ResTrans1044BasePartEntity getResTrans1044BasePartEntity() {
		return resTrans1044BasePartEntity;
	}

	public void setResTrans1044BasePartEntity(
			ResTrans1044BasePartEntity resTrans1044BasePartEntity) {
		this.resTrans1044BasePartEntity = resTrans1044BasePartEntity;
	}
	
	
}
