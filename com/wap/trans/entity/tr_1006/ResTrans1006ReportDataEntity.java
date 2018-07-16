package com.wap.trans.entity.tr_1006;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("REPORT_DATA")
public class ResTrans1006ReportDataEntity {
	@XStreamAlias("REGISTNO")
	private String registno;// 报案号
	@XStreamAlias("LICENSENO")
	private String licenseno;// 车牌号
	@XStreamAlias("DAMAGEADDRESS")
	private String damageaddress;// 出险地点
	@XStreamAlias("REPORTTIME")
	private String reporttime;// 报案时间
	@XStreamAlias("CASESTATUS")
	private String casestatus;// 案件状态

	public String getRegistno() {
		return registno;
	}

	public void setRegistno(String registno) {
		this.registno = registno;
	}

	public String getLicenseno() {
		return licenseno;
	}

	public void setLicenseno(String licenseno) {
		this.licenseno = licenseno;
	}

	public String getDamageaddress() {
		return damageaddress;
	}

	public void setDamageaddress(String damageaddress) {
		this.damageaddress = damageaddress;
	}

	public String getReporttime() {
		return reporttime;
	}

	public void setReporttime(String reporttime) {
		this.reporttime = reporttime;
	}

	public String getCasestatus() {
		return casestatus;
	}

	public void setCasestatus(String casestatus) {
		this.casestatus = casestatus;
	}

}
