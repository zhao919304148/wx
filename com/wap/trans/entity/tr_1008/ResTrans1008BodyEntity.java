package com.wap.trans.entity.tr_1008;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1008BodyEntity {
	@XStreamAlias("BASE_PART")
	private ResTrans1008BasePartEntity resTrans1008BasePartEntity;
	@XStreamAlias("COMPONENT_LIST")
	private List<ResTrans1008ComponentDataEntity> resTrans1008ComponentDataEntityList;

	public ResTrans1008BasePartEntity getResTrans1008BasePartEntity() {
		return resTrans1008BasePartEntity;
	}

	public void setResTrans1008BasePartEntity(
			ResTrans1008BasePartEntity resTrans1008BasePartEntity) {
		this.resTrans1008BasePartEntity = resTrans1008BasePartEntity;
	}

	public List<ResTrans1008ComponentDataEntity> getResTrans1008ComponentDataEntityList() {
		return resTrans1008ComponentDataEntityList;
	}

	public void setResTrans1008ComponentDataEntityList(
			List<ResTrans1008ComponentDataEntity> resTrans1008ComponentDataEntityList) {
		this.resTrans1008ComponentDataEntityList = resTrans1008ComponentDataEntityList;
	}

}
