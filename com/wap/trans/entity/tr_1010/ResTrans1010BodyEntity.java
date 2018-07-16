package com.wap.trans.entity.tr_1010;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1010BodyEntity {
	@XStreamAlias("BASE_PART")
	private ResTrans1010BasePartEntity resTrans1010BasePartEntity;
	@XStreamAlias("MATERIAL_LIST")
	private List<ResTrans1010MaterialDataEntity> resTrans1010MaterialDataEntityList;

	public ResTrans1010BasePartEntity getResTrans1010BasePartEntity() {
		return resTrans1010BasePartEntity;
	}

	public void setResTrans1010BasePartEntity(
			ResTrans1010BasePartEntity resTrans1010BasePartEntity) {
		this.resTrans1010BasePartEntity = resTrans1010BasePartEntity;
	}

	public List<ResTrans1010MaterialDataEntity> getResTrans1010MaterialDataEntityList() {
		return resTrans1010MaterialDataEntityList;
	}

	public void setResTrans1010MaterialDataEntityList(
			List<ResTrans1010MaterialDataEntity> resTrans1010MaterialDataEntityList) {
		this.resTrans1010MaterialDataEntityList = resTrans1010MaterialDataEntityList;
	}

}
