package com.wap.trans.entity.tr_1006;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1006BodyEntity {
	@XStreamAlias("REPORT_LIST")
	private List<ResTrans1006ReportDataEntity> resTrans1006ReportDataEntityList;

	public List<ResTrans1006ReportDataEntity> getResTrans1006ReportDataEntityList() {
		return resTrans1006ReportDataEntityList;
	}

	public void setResTrans1006ReportDataEntityList(
			List<ResTrans1006ReportDataEntity> resTrans1006ReportDataEntityList) {
		this.resTrans1006ReportDataEntityList = resTrans1006ReportDataEntityList;
	}

}
