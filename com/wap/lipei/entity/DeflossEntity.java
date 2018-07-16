package com.wap.lipei.entity;

/**
 * 
 * 
 * 描述:定损信息实体类
 * 
 * @author 朱久满
 * @version 1.0
 * @since 2015年12月29日 下午4:54:43
 */
public class DeflossEntity {
	private String handlercode;// 定损员代码
	private String handlername;// 定损员名称
	private String deflossmobile;// 定损员电话
	private String defstarttime;// 定损开始时间
	private String defendtime;// 定损结束时间
	private String enddeflossflag;// 定损完成标志
	private String licenseno;// 车牌号
	private String prpldeflossmainid;// 定损任务主键ID
	private String sumlossfee;// 定损合计金额
	private String sumremnant;// 定损总残值金额
	private String compfee;// 配件费总计
	private String repairfee;// 修理费总计
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
		if(sumlossfee == null || "".equals(sumlossfee.trim()))
			sumlossfee = "0.00";
		return sumlossfee;
	}

	public void setSumlossfee(String sumlossfee) {
		this.sumlossfee = sumlossfee;
	}

	public String getSumremnant() {
		if(sumremnant == null || "".equals(sumremnant.trim()))
			sumremnant = "0.00";
		return sumremnant;
	}

	public void setSumremnant(String sumremnant) {
		this.sumremnant = sumremnant;
	}

	public String getCompfee() {
		if(compfee == null || "".equals(compfee.trim()))
			compfee = "0.00";
		return compfee;
	}

	public void setCompfee(String compfee) {
		this.compfee = compfee;
	}

	public String getRepairfee() {
		if(repairfee == null || "".equals(repairfee.trim()))
			repairfee = "0.00";
		return repairfee;
	}

	public void setRepairfee(String repairfee) {
		this.repairfee = repairfee;
	}

	public String getMaterialfee() {
		if(materialfee == null || "".equals(materialfee.trim()))
			materialfee = "0.00";
		return materialfee;
	}

	public void setMaterialfee(String materialfee) {
		this.materialfee = materialfee;
	}

}
