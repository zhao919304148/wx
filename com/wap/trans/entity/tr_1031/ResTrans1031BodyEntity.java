package com.wap.trans.entity.tr_1031;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;
import com.wap.trans.entity.tr_1029.ScoreOrder;

@XStreamAlias("BODY")
public class ResTrans1031BodyEntity {
	@XStreamAlias("BASE_PART")
	private ResTrans1031BodyBasePart basePart;
	
	@XStreamAlias("SCORE_LIST")
	private List<ResTrans1031ScoreDeailEntity> scoreList;

	public List<ResTrans1031ScoreDeailEntity> getScoreList() {
		return scoreList;
	}

	public void setScoreList(List<ResTrans1031ScoreDeailEntity> scoreList) {
		this.scoreList = scoreList;
	}

	public ResTrans1031BodyBasePart getBasePart() {
		return basePart;
	}

	public void setBasePart(ResTrans1031BodyBasePart basePart) {
		this.basePart = basePart;
	}




}
