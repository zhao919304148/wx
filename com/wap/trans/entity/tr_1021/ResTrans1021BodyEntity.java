package com.wap.trans.entity.tr_1021;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1021BodyEntity {

	@XStreamAlias("BASE_PART")
	private ResTrans1021BasePartEntity   resTrans1021BasePartEntity;
	
	@XStreamAlias("SCORE")
	private ResTrans1021Score resTrans1021Score;
	
	public ResTrans1021Score getResTrans1021Score() {
		return resTrans1021Score;
	}

	public void setResTrans1021Score(ResTrans1021Score resTrans1021Score) {
		this.resTrans1021Score = resTrans1021Score;
	}

	@XStreamAlias("CARD_LIST")
	private List<ResTrans1021CardDataEntity>   resTrans1021CardDataEntityList;

	public ResTrans1021BasePartEntity getResTrans1021BasePartEntity() {
		return resTrans1021BasePartEntity;
	}

	public void setResTrans1021BasePartEntity(ResTrans1021BasePartEntity resTrans1021BasePartEntity) {
		this.resTrans1021BasePartEntity = resTrans1021BasePartEntity;
	}

	public List<ResTrans1021CardDataEntity> getResTrans1021CardDataEntityList() {
		return resTrans1021CardDataEntityList;
	}

	public void setResTrans1021CardDataEntityList(List<ResTrans1021CardDataEntity> resTrans1021CardDataEntityList) {
		this.resTrans1021CardDataEntityList = resTrans1021CardDataEntityList;
	}
	
	
}
