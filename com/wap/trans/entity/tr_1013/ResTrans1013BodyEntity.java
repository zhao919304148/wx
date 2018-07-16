package com.wap.trans.entity.tr_1013;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1013BodyEntity {
	@XStreamAlias("BASE_PART")
	private ResTrans1013BasePartEntity resTrans1013BasePartEntity;
	@XStreamAlias("FILE_LIST")
	private List<ResTrans1013FileDataEntity> resTrans1013FileDataEntityList;

	public ResTrans1013BasePartEntity getResTrans1013BasePartEntity() {
		return resTrans1013BasePartEntity;
	}

	public void setResTrans1013BasePartEntity(
			ResTrans1013BasePartEntity resTrans1013BasePartEntity) {
		this.resTrans1013BasePartEntity = resTrans1013BasePartEntity;
	}

	public List<ResTrans1013FileDataEntity> getResTrans1013FileDataEntityList() {
		return resTrans1013FileDataEntityList;
	}

	public void setResTrans1013FileDataEntityList(
			List<ResTrans1013FileDataEntity> resTrans1013FileDataEntityList) {
		this.resTrans1013FileDataEntityList = resTrans1013FileDataEntityList;
	}

}
