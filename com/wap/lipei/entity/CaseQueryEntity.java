package com.wap.lipei.entity;

/**
 * 
		
		* 描述:报案列表查询条件
		*
		* @author 朱久满
		* @version 1.0
		* @since 2015年12月29日 下午4:54:19
 */
public class CaseQueryEntity {
	private String identifynumber;// 身份证号码
	private String registno;// 报案号
	private String reportormobile;// 手机号
	private String licenseno;// 车牌号
	private String casestatus;// 案件状态

	public String getIdentifynumber() {
		return identifynumber;
	}

	public void setIdentifynumber(String identifynumber) {
		this.identifynumber = identifynumber;
	}

	public String getRegistno() {
		return registno;
	}

	public void setRegistno(String registno) {
		this.registno = registno;
	}

	public String getReportormobile() {
		return reportormobile;
	}

	public void setReportormobile(String reportormobile) {
		this.reportormobile = reportormobile;
	}

	public String getLicenseno() {
		return licenseno;
	}

	public void setLicenseno(String licenseno) {
		this.licenseno = licenseno;
	}

	public String getCasestatus() {
		return casestatus;
	}

	public void setCasestatus(String casestatus) {
		this.casestatus = casestatus;
	}

}
