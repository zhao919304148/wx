package com.wap.trans.entity.tr_1003;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1003BodyEntity {
	@XStreamAlias("ORDER_INFO")
	private ResTrans1003OrderInfoEntity resTrans1003OrderInfoEntity;
	@XStreamAlias("FILE_LIST")
	private List<ResTrans1003FileDataEntity> resTrans1003FileDataEntityList;

	public ResTrans1003OrderInfoEntity getResTrans1003OrderInfoEntity() {
		return resTrans1003OrderInfoEntity;
	}

	public void setResTrans1003OrderInfoEntity(
			ResTrans1003OrderInfoEntity resTrans1003OrderInfoEntity) {
		this.resTrans1003OrderInfoEntity = resTrans1003OrderInfoEntity;
	}

	public List<ResTrans1003FileDataEntity> getResTrans1003FileDataEntityList() {
		return resTrans1003FileDataEntityList;
	}

	public void setResTrans1003FileDataEntityList(
			List<ResTrans1003FileDataEntity> resTrans1003FileDataEntityList) {
		this.resTrans1003FileDataEntityList = resTrans1003FileDataEntityList;
	}

}
