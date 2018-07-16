package com.wap.trans.entity.tr_1046;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ResTrans1046BodyEntity {
	
	@XStreamAlias("BASE_PART")
	private ResTrans1046BasePartEntity resTrans1046BasePartEntity;
	
	@XStreamAlias("CARD_LIST")
	private List<ResTrans1046CardDataEntity> resTrans1046CardDataEntityList;

	public ResTrans1046BasePartEntity getResTrans1046BasePartEntity() {
		return resTrans1046BasePartEntity;
	}

	public void setResTrans1046BasePartEntity(
			ResTrans1046BasePartEntity resTrans1046BasePartEntity) {
		this.resTrans1046BasePartEntity = resTrans1046BasePartEntity;
	}

	public List<ResTrans1046CardDataEntity> getResTrans1046CardDataEntityList() {
		return resTrans1046CardDataEntityList;
	}

	public void setResTrans1046CardDataEntityList(
			List<ResTrans1046CardDataEntity> resTrans1046CardDataEntityList) {
		this.resTrans1046CardDataEntityList = resTrans1046CardDataEntityList;
	}

}
