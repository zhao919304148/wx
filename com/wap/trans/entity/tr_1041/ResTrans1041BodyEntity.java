package com.wap.trans.entity.tr_1041;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.tr_1021.ResTrans1021BasePartEntity;

@XStreamAlias("BODY")
public class ResTrans1041BodyEntity {
	
	@XStreamAlias("BASE_PART")
	private ResTrans1041BasePartEntity   resTrans1041BasePartEntity;

	public ResTrans1041BasePartEntity getResTrans1041BasePartEntity() {
		return resTrans1041BasePartEntity;
	}

	public void setResTrans1041BasePartEntity(
			ResTrans1041BasePartEntity resTrans1041BasePartEntity) {
		this.resTrans1041BasePartEntity = resTrans1041BasePartEntity;
	}
	
}
