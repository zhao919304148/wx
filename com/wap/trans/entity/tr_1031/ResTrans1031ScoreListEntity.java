package com.wap.trans.entity.tr_1031;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SCORE_LIST")
public class ResTrans1031ScoreListEntity {

	@XStreamAlias("SCORE_DEAIL")
	private ResTrans1031ScoreDeailEntity resTrans1031ScoreDeailEntity;

	public ResTrans1031ScoreDeailEntity getResTrans1031ScoreDeailEntity() {
		return resTrans1031ScoreDeailEntity;
	}

	public void setResTrans1031ScoreDeailEntity(ResTrans1031ScoreDeailEntity resTrans1031ScoreDeailEntity) {
		this.resTrans1031ScoreDeailEntity = resTrans1031ScoreDeailEntity;
	}
	
}
