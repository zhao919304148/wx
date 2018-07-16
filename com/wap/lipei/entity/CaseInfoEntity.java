package com.wap.lipei.entity;

import java.util.List;

import com.sys.dic.SysDicHelper;

/**
 * 
		
		* 描述:案件详情实体类
		*
		* @author 朱久满
		* @version 1.0
		* @since 2015年12月29日 下午4:53:54
 */
public class CaseInfoEntity {
	private String registno;// 报案号
	private String insuredtype;// 客户类型
	private String licenseno;// 车牌号
	private String damageaddress;// 出险地点
	private String reporttime;// 报案时间
	private String damagetime;// 出险时间
	private String casestatus;// 案件状态
	private String reportorname;// 报案人
	private String reportormobile;// 报案人电话
	private String checkercode;// 查勘人代码
	private String checkername;// 查勘人名称
	private String checkermobile;// 查勘人电话
	private String ckstarttime;// 查勘开始时间
	private String ckendtime;// 查勘结束时间
	private String filecollcode;// 资料收集员工号
	private String filecollname;// 资料收集员姓名
	private String filecollmobile;// 资料收集员电话
	private String filecollstarttime;// 资料收集开始时间
	private String filecollendtime;// 资料收集结束时间
	private String filecollcheckinfo;// 索赔材料审核不通过提示信息
	private String sumrealpay;// 总支付金额
	private String hurtflag;// 是否涉及人伤
	private List<DeflossEntity> deflossList;// 定损信息列表
	private List<PayfeeEntity> payfeeList;// 支付赔款信息列表

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

	public String getReportorname() {
		return reportorname;
	}

	public void setReportorname(String reportorname) {
		this.reportorname = reportorname;
	}

	public String getReportormobile() {
		return reportormobile;
	}

	public void setReportormobile(String reportormobile) {
		this.reportormobile = reportormobile;
	}

	public String getCheckercode() {
		return checkercode;
	}

	public void setCheckercode(String checkercode) {
		this.checkercode = checkercode;
	}

	public String getCheckername() {
		return checkername;
	}

	public void setCheckername(String checkername) {
		this.checkername = checkername;
	}

	public String getCheckermobile() {
		return checkermobile;
	}

	public void setCheckermobile(String checkermobile) {
		this.checkermobile = checkermobile;
	}

	public String getCkstarttime() {
		return ckstarttime;
	}

	public void setCkstarttime(String ckstarttime) {
		this.ckstarttime = ckstarttime;
	}

	public String getCkendtime() {
		return ckendtime;
	}

	public void setCkendtime(String ckendtime) {
		this.ckendtime = ckendtime;
	}

	public String getFilecollcode() {
		return filecollcode;
	}

	public void setFilecollcode(String filecollcode) {
		this.filecollcode = filecollcode;
	}

	public String getFilecollname() {
		return filecollname;
	}

	public void setFilecollname(String filecollname) {
		this.filecollname = filecollname;
	}

	public String getFilecollmobile() {
		return filecollmobile;
	}

	public void setFilecollmobile(String filecollmobile) {
		this.filecollmobile = filecollmobile;
	}

	public String getFilecollstarttime() {
		return filecollstarttime;
	}

	public void setFilecollstarttime(String filecollstarttime) {
		this.filecollstarttime = filecollstarttime;
	}

	public String getFilecollendtime() {
		return filecollendtime;
	}

	public void setFilecollendtime(String filecollendtime) {
		this.filecollendtime = filecollendtime;
	}

	public String getFilecollcheckinfo() {
		return filecollcheckinfo;
	}

	public void setFilecollcheckinfo(String filecollcheckinfo) {
		this.filecollcheckinfo = filecollcheckinfo;
	}

	public String getSumrealpay() {
		return sumrealpay;
	}

	public void setSumrealpay(String sumrealpay) {
		this.sumrealpay = sumrealpay;
	}

	public String getHurtflag() {
		return hurtflag;
	}

	public void setHurtflag(String hurtflag) {
		this.hurtflag = hurtflag;
	}

	public List<DeflossEntity> getDeflossList() {
		return deflossList;
	}

	public void setDeflossList(List<DeflossEntity> deflossList) {
		this.deflossList = deflossList;
	}

	public List<PayfeeEntity> getPayfeeList() {
		return payfeeList;
	}

	public void setPayfeeList(List<PayfeeEntity> payfeeList) {
		this.payfeeList = payfeeList;
	}

	public String getInsuredtype() {
		return insuredtype;
	}

	public void setInsuredtype(String insuredtype) {
		this.insuredtype = insuredtype;
	}

	public String getDamagetime() {
		return damagetime;
	}

	public void setDamagetime(String damagetime) {
		this.damagetime = damagetime;
	}
	public String getCasestatusname(){
		return SysDicHelper.getInstance().getValueByDicTypeAndDicId("CASESTATUS", this.casestatus);
	}
	
}
