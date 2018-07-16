package com.wap.trans.entity.tr_1058;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ResTrans1058BodyEntity {
	
	@XStreamAlias("BASE_PART")
	private ResTrans1058BasePartEntity rsTrans1058BasePartEntity;
    
	@XStreamAlias("CONSUME_LIST")
	private List<ResTrans1058CardDataEntity> resTrans1058CardDataEntityList;

	public ResTrans1058BasePartEntity getRsTrans1058BasePartEntity() {
		return rsTrans1058BasePartEntity;
	}

	public void setRsTrans1058BasePartEntity(
			ResTrans1058BasePartEntity rsTrans1058BasePartEntity) {
		this.rsTrans1058BasePartEntity = rsTrans1058BasePartEntity;
	}

	public List<ResTrans1058CardDataEntity> getResTrans1058CardDataEntityList() {
		return resTrans1058CardDataEntityList;
	}

	public void setResTrans1058CardDataEntityList(
			List<ResTrans1058CardDataEntity> resTrans1058CardDataEntityList) {
		this.resTrans1058CardDataEntityList = resTrans1058CardDataEntityList;
	}
	
	
}
