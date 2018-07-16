package com.wap.trans.entity.tr_1005;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1005BodyEntity {
	@XStreamAlias("BASE_PART")
	private ResTrans1005BasePartEntity resTrans1005BasePartEntity;
	@XStreamAlias("FILE_LIST")
	private List<ResTrans1005FileDataEntity> resTrans1005FileDataEntityList;

	public ResTrans1005BasePartEntity getResTrans1005BasePartEntity() {
		return resTrans1005BasePartEntity;
	}

	public void setResTrans1005BasePartEntity(
			ResTrans1005BasePartEntity resTrans1005BasePartEntity) {
		this.resTrans1005BasePartEntity = resTrans1005BasePartEntity;
	}

	public List<ResTrans1005FileDataEntity> getResTrans1005FileDataEntityList() {
		return resTrans1005FileDataEntityList;
	}

	public void setResTrans1005FileDataEntityList(
			List<ResTrans1005FileDataEntity> resTrans1005FileDataEntityList) {
		this.resTrans1005FileDataEntityList = resTrans1005FileDataEntityList;
	}

}
