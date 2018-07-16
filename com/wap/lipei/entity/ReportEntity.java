package com.wap.lipei.entity;

/**
 * 
		
		* 描述:报案列表信息实体类
		*
		* @author 朱久满
		* @version 1.0
		* @since 2015年12月29日 下午4:55:44
 */
public class ReportEntity {
	private String registno;// 报案号
	private String licenseno;// 车牌号
	private String damageaddress;// 出险地点
	private String reporttime;// 报案时间
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
