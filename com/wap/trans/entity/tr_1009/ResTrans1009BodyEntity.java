package com.wap.trans.entity.tr_1009;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1009BodyEntity {
	@XStreamAlias("BASE_PART")
	private ResTrans1009BasePartEntity resTrans1009BasePartEntity;
	@XStreamAlias("REPAIR_LIST")
	private List<ResTrans1009RepairDataEntity> resTrans1009RepairDataEntityList;

	public ResTrans1009BasePartEntity getResTrans1009BasePartEntity() {
		return resTrans1009BasePartEntity;
	}

	public void setResTrans1009BasePartEntity(
			ResTrans1009BasePartEntity resTrans1009BasePartEntity) {
		this.resTrans1009BasePartEntity = resTrans1009BasePartEntity;
	}

	public List<ResTrans1009RepairDataEntity> getResTrans1009RepairDataEntityList() {
		return resTrans1009RepairDataEntityList;
	}

	public void setResTrans1009RepairDataEntityList(
			List<ResTrans1009RepairDataEntity> resTrans1009RepairDataEntityList) {
		this.resTrans1009RepairDataEntityList = resTrans1009RepairDataEntityList;
	}

}
