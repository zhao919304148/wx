package com.wap.trans.entity.tr_1012;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1012BodyEntity {
	@XStreamAlias("BASE_PART")
	private ResTrans1012BasePartEntity resTrans1012BasePartEntity;
	@XStreamAlias("FILE_LIST")
	private List<ResTrans1012FileDataEntity> resTrans1012FileDataEntityList;

	public ResTrans1012BasePartEntity getResTrans1012BasePartEntity() {
		return resTrans1012BasePartEntity;
	}

	public void setResTrans1012BasePartEntity(
			ResTrans1012BasePartEntity resTrans1012BasePartEntity) {
		this.resTrans1012BasePartEntity = resTrans1012BasePartEntity;
	}

	public List<ResTrans1012FileDataEntity> getResTrans1012FileDataEntityList() {
		return resTrans1012FileDataEntityList;
	}

	public void setResTrans1012FileDataEntityList(
			List<ResTrans1012FileDataEntity> resTrans1012FileDataEntityList) {
		this.resTrans1012FileDataEntityList = resTrans1012FileDataEntityList;
	}

}
