package com.wap.trans.entity.tr_1030;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;
import com.wap.trans.entity.tr_1029.ScoreOrder;

@XStreamAlias("BODY")
public class ResTrans1030BodyEntity {
	
	@XStreamAlias("ORDERINFO")
	private ResTrans1030OrderEntity scoreOrder;

	public ResTrans1030OrderEntity getScoreOrder() {
		return scoreOrder;
	}

	public void setScoreOrder(ResTrans1030OrderEntity scoreOrder) {
		this.scoreOrder = scoreOrder;
	}

}
