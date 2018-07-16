package com.wap.trans.entity.tr_1040;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1040BodyEntity {
	
	@XStreamAlias("BASE_PART")
	private ResTrans1040BasePartEntity   resTrans1040BasePartEntity;

	public ResTrans1040BasePartEntity getResTrans1040BasePartEntity() {
		return resTrans1040BasePartEntity;
	}

	public void setResTrans1040BasePartEntity(
			ResTrans1040BasePartEntity resTrans1040BasePartEntity) {
		this.resTrans1040BasePartEntity = resTrans1040BasePartEntity;
	}
	
	
}
