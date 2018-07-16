package com.wap.trans.entity.tr_1002;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1002BodyEntity {
	@XStreamAlias("BASE_PART")
	private ResTrans1002BasePartEntity resTrans1002BasePartEntity;
	@XStreamAlias("ORDER_LIST")
	private List<ResTrans1002OrderDataEntity> resTrans1002OrderDataEntityList;

	public ResTrans1002BasePartEntity getResTrans1002BasePartEntity() {
		return resTrans1002BasePartEntity;
	}

	public void setResTrans1002BasePartEntity(
			ResTrans1002BasePartEntity resTrans1002BasePartEntity) {
		this.resTrans1002BasePartEntity = resTrans1002BasePartEntity;
	}

	public List<ResTrans1002OrderDataEntity> getResTrans1002OrderDataEntityList() {
		return resTrans1002OrderDataEntityList;
	}

	public void setResTrans1002OrderDataEntityList(
			List<ResTrans1002OrderDataEntity> resTrans1002OrderDataEntityList) {
		this.resTrans1002OrderDataEntityList = resTrans1002OrderDataEntityList;
	}

}
