package com.wap.trans.entity.tr_1007;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1007BodyEntity {
	@XStreamAlias("REPORT_INFO")
	private ResTrans1007ReportInfoEntity resTrans1007ReportInfoEntity;
	@XStreamAlias("DEFLOSS_LIST")
	private List<ResTrans1007DeflossDataEntity> resTrans1007DeflossDataEntityList;
	@XStreamAlias("PAYFEE_LIST")
	private List<ResTrans1007PayfeeDataEntity> resTrans1007PayfeeDataEntityList;

	public ResTrans1007ReportInfoEntity getResTrans1007ReportInfoEntity() {
		return resTrans1007ReportInfoEntity;
	}

	public void setResTrans1007ReportInfoEntity(
			ResTrans1007ReportInfoEntity resTrans1007ReportInfoEntity) {
		this.resTrans1007ReportInfoEntity = resTrans1007ReportInfoEntity;
	}

	public List<ResTrans1007DeflossDataEntity> getResTrans1007DeflossDataEntityList() {
		return resTrans1007DeflossDataEntityList;
	}

	public void setResTrans1007DeflossDataEntityList(
			List<ResTrans1007DeflossDataEntity> resTrans1007DeflossDataEntityList) {
		this.resTrans1007DeflossDataEntityList = resTrans1007DeflossDataEntityList;
	}

	public List<ResTrans1007PayfeeDataEntity> getResTrans1007PayfeeDataEntityList() {
		return resTrans1007PayfeeDataEntityList;
	}

	public void setResTrans1007PayfeeDataEntityList(
			List<ResTrans1007PayfeeDataEntity> resTrans1007PayfeeDataEntityList) {
		this.resTrans1007PayfeeDataEntityList = resTrans1007PayfeeDataEntityList;
	}

}
