package com.wap.trans.entity.tr_1011;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1011BodyEntity {
	@XStreamAlias("BASE_PART")
	private ResTrans1011BasePartEntity resTrans1011BasePartEntity;
	@XStreamAlias("COMPONENT_LIST")
	private List<ResTrans1011ComponentDataEntity> resTrans1011ComponentDataEntityList;

	public ResTrans1011BasePartEntity getResTrans1011BasePartEntity() {
		return resTrans1011BasePartEntity;
	}

	public void setResTrans1011BasePartEntity(
			ResTrans1011BasePartEntity resTrans1011BasePartEntity) {
		this.resTrans1011BasePartEntity = resTrans1011BasePartEntity;
	}

	public List<ResTrans1011ComponentDataEntity> getResTrans1011ComponentDataEntityList() {
		return resTrans1011ComponentDataEntityList;
	}

	public void setResTrans1011ComponentDataEntityList(
			List<ResTrans1011ComponentDataEntity> resTrans1011ComponentDataEntityList) {
		this.resTrans1011ComponentDataEntityList = resTrans1011ComponentDataEntityList;
	}

}
