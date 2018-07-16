package com.wap.trans.entity.tr_1072;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ResTrans1072BasePartEntity {
	
	@XStreamAlias("REPORT_DATA")
	private String reportData = null;

	public String getReportData() {
		return reportData;
	}

	public void setReportData(String reportData) {
		this.reportData = reportData;
	}

}
