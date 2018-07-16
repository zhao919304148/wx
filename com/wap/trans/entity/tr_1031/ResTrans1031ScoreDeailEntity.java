package com.wap.trans.entity.tr_1031;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SCORE_DEAIL")
public class ResTrans1031ScoreDeailEntity {

	@XStreamAlias("CHANGE_TIME")
	private String changeTime;
	@XStreamAlias("CHANGE_SCORE")
	private String changeScore;
	@XStreamAlias("CHANGE_MATTER")
	private String changeMatter;
	@XStreamAlias("SCORE_BALANCE")
	private String scoreBalace;
	public String getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(String changeTime) {
		this.changeTime = changeTime;
	}
	public String getChangeScore() {
		return changeScore;
	}
	public void setChangeScore(String changeScore) {
		this.changeScore = changeScore;
	}
	public String getChangeMatter() {
		return changeMatter;
	}
	public void setChangeMatter(String changeMatter) {
		this.changeMatter = changeMatter;
	}
	public String getScoreBalace() {
		return scoreBalace;
	}
	public void setScoreBalace(String scoreBalace) {
		this.scoreBalace = scoreBalace;
	}
	
}
