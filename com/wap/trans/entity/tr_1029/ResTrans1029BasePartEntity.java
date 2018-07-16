package com.wap.trans.entity.tr_1029;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BASE_PART")
public class ResTrans1029BasePartEntity {
 
	@XStreamAlias("SCORE_BALANCE")
	private String scoreBalance;   //积分余额

	public String getScoreBalance() {
		return scoreBalance;
	}

	public void setScoreBalance(String scoreBalance) {
		this.scoreBalance = scoreBalance;
	}

	
	
	
	
}
