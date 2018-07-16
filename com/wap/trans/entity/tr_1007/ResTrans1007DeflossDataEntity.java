package com.wap.trans.entity.tr_1007;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("DEFLOSS_DATA")
public class ResTrans1007DeflossDataEntity {
	@XStreamAlias("HANDLERCODE")
	private String handlercode;// 定损员代码
	@XStreamAlias("HANDLERNAME")
	private String handlername;// 定损员名称
	@XStreamAlias("DEFLOSSMOBILE")
	private String deflossmobile;// 定损员电话
	@XStreamAlias("DEFSTARTTIME")
	private String defstarttime;// 定损开始时间
	@XStreamAlias("DEFENDTIME")
	private String defendtime;// 定损结束时间
	@XStreamAlias("ENDDEFLOSSFLAG")
	private String enddeflossflag;// 定损完成标志
	@XStreamAlias("LICENSENO")
	private String licenseno;// 车牌号
	@XStreamAlias("PRPLDEFLOSSMAINID")
	private String prpldeflossmainid;// 定损任务主键ID
	@XStreamAlias("SUMLOSSFEE")
	private String sumlossfee;// 定损合计金额
	@XStreamAlias("SUMREMNANT")
	private String sumremnant;// 定损总残值金额
	@XStreamAlias("COMPFEE")
	private String compfee;// 配件费总计
	@XStreamAlias("REPAIRFEE")
	private String repairfee;// 修理费总计
	@XStreamAlias("MATERIALFEE")
	private String materialfee;// 辅料费总计

	public String getHandlercode() {
		return handlercode;
	}

	public void setHandlercode(String handlercode) {
		this.handlercode = handlercode;
	}

	public String getHandlername() {
		return handlername;
	}

	public void setHandlername(String handlername) {
		this.handlername = handlername;
	}

	public String getDeflossmobile() {
		return deflossmobile;
	}

	public void setDeflossmobile(String deflossmobile) {
		this.deflossmobile = deflossmobile;
	}

	public String getDefstarttime() {
		return defstarttime;
	}

	public void setDefstarttime(String defstarttime) {
		this.defstarttime = defstarttime;
	}

	public String getDefendtime() {
		return defendtime;
	}

	public void setDefendtime(String defendtime) {
		this.defendtime = defendtime;
	}

	public String getEnddeflossflag() {
		return enddeflossflag;
	}

	public void setEnddeflossflag(String enddeflossflag) {
		this.enddeflossflag = enddeflossflag;
	}

	public String getLicenseno() {
		return licenseno;
	}

	public void setLicenseno(String licenseno) {
		this.licenseno = licenseno;
	}

	public String getPrpldeflossmainid() {
		return prpldeflossmainid;
	}

	public void setPrpldeflossmainid(String prpldeflossmainid) {
		this.prpldeflossmainid = prpldeflossmainid;
	}

	public String getSumlossfee() {
		return sumlossfee;
	}

	public void setSumlossfee(String sumlossfee) {
		this.sumlossfee = sumlossfee;
	}

	public String getSumremnant() {
		return sumremnant;
	}

	public void setSumremnant(String sumremnant) {
		this.sumremnant = sumremnant;
	}

	public String getCompfee() {
		return compfee;
	}

	public void setCompfee(String compfee) {
		this.compfee = compfee;
	}

	public String getRepairfee() {
		return repairfee;
	}

	public void setRepairfee(String repairfee) {
		this.repairfee = repairfee;
	}

	public String getMaterialfee() {
		return materialfee;
	}

	public void setMaterialfee(String materialfee) {
		this.materialfee = materialfee;
	}

}
