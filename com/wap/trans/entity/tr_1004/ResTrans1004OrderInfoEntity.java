package com.wap.trans.entity.tr_1004;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ORDER_INFO")
public class ResTrans1004OrderInfoEntity {
	@XStreamAlias("ORDERNO")
	private String orderno; // 订单号
	@XStreamAlias("LICENSENO")
	private String licenseno; // 车牌号
	@XStreamAlias("JQSTARTDATE")
	private String jqstartdate; // 交强险生效日期
	@XStreamAlias("SYSTARTDATE")
	private String systartdate; // 商业险生效日期
	@XStreamAlias("AREACOMNAME")
	private String areacomname; // 区县名称
	@XStreamAlias("CONSIGNEE")
	private String consignee; // 收件人
	@XStreamAlias("ADDRESS")
	private String address; // 地址
	@XStreamAlias("CHARGEDATE")
	private String chargedate; // 预定收费日期/预订送单日期
	@XStreamAlias("MODIFYDATE")
	private String modifydate; // 修改收费日期/修改送单日期
	@XStreamAlias("ORDERSTATUS")
	private String orderstatus; // 订单状态
	@XStreamAlias("PAYTYPE")
	private String paytype; // 支付方式
	@XStreamAlias("PRINTTIME")
	private String printtime; // 打印时间
	@XStreamAlias("HANDLERCODE")
	private String handlercode; // 坐席工号
	@XStreamAlias("HANDLERREMARK")
	private String handlerremark; // 坐席备注
	@XStreamAlias("PREMIUMBZ")
	private String premiumbz; // 交强险金额
	@XStreamAlias("PREMIUMSY")
	private String premiumsy; // 商业险金额
	@XStreamAlias("AMOUNTCCTAX")
	private String amountcctax; // 车船税金额
	@XStreamAlias("AMOUNTSF")
	private String amountsf; // 收费金额
	@XStreamAlias("COURIER")
	private String courier; // 送单员代码
	@XStreamAlias("COURIERNAME")
	private String couriername; // 送单员名称
	@XStreamAlias("PRINTORCODE")
	private String printorcode; // 打单员工号
	@XStreamAlias("PRINTORNAME")
	private String printorname; // 打单员名称
	@XStreamAlias("BARCODE")
	private String barcode; // 条码
	@XStreamAlias("ONEBACKREASON")
	private String onebackreason; // 一级反馈原因
	@XStreamAlias("TWOBACKREASON")
	private String twobackreason; // 二级反馈原因
	@XStreamAlias("THRBACKREASON")
	private String thrbackreason; // 三级反馈原因
	@XStreamAlias("BACKREMARK")
	private String backremark; // 反馈备注
	@XStreamAlias("REMARK")
	private String remark; // 备注
	@XStreamAlias("GIFTNAME")
	private String giftname; // 礼品名称
	@XStreamAlias("ISCARCHECK")
	private String iscarcheck; // 是否验车
	@XStreamAlias("QUERYFLAG")
	private String queryflag; // 配送单标识

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getLicenseno() {
		return licenseno;
	}

	public void setLicenseno(String licenseno) {
		this.licenseno = licenseno;
	}

	public String getJqstartdate() {
		return jqstartdate;
	}

	public void setJqstartdate(String jqstartdate) {
		this.jqstartdate = jqstartdate;
	}

	public String getSystartdate() {
		return systartdate;
	}

	public void setSystartdate(String systartdate) {
		this.systartdate = systartdate;
	}

	public String getAreacomname() {
		return areacomname;
	}

	public void setAreacomname(String areacomname) {
		this.areacomname = areacomname;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getChargedate() {
		return chargedate;
	}

	public void setChargedate(String chargedate) {
		this.chargedate = chargedate;
	}

	public String getModifydate() {
		return modifydate;
	}

	public void setModifydate(String modifydate) {
		this.modifydate = modifydate;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getPrinttime() {
		return printtime;
	}

	public void setPrinttime(String printtime) {
		this.printtime = printtime;
	}

	public String getHandlercode() {
		return handlercode;
	}

	public void setHandlercode(String handlercode) {
		this.handlercode = handlercode;
	}

	public String getHandlerremark() {
		return handlerremark;
	}

	public void setHandlerremark(String handlerremark) {
		this.handlerremark = handlerremark;
	}

	public String getPremiumbz() {
		return premiumbz;
	}

	public void setPremiumbz(String premiumbz) {
		this.premiumbz = premiumbz;
	}

	public String getPremiumsy() {
		return premiumsy;
	}

	public void setPremiumsy(String premiumsy) {
		this.premiumsy = premiumsy;
	}

	public String getAmountcctax() {
		return amountcctax;
	}

	public void setAmountcctax(String amountcctax) {
		this.amountcctax = amountcctax;
	}

	public String getAmountsf() {
		return amountsf;
	}

	public void setAmountsf(String amountsf) {
		this.amountsf = amountsf;
	}

	public String getCourier() {
		return courier;
	}

	public void setCourier(String courier) {
		this.courier = courier;
	}

	public String getCouriername() {
		return couriername;
	}

	public void setCouriername(String couriername) {
		this.couriername = couriername;
	}

	public String getPrintorcode() {
		return printorcode;
	}

	public void setPrintorcode(String printorcode) {
		this.printorcode = printorcode;
	}

	public String getPrintorname() {
		return printorname;
	}

	public void setPrintorname(String printorname) {
		this.printorname = printorname;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getOnebackreason() {
		return onebackreason;
	}

	public void setOnebackreason(String onebackreason) {
		this.onebackreason = onebackreason;
	}

	public String getTwobackreason() {
		return twobackreason;
	}

	public void setTwobackreason(String twobackreason) {
		this.twobackreason = twobackreason;
	}

	public String getThrbackreason() {
		return thrbackreason;
	}

	public void setThrbackreason(String thrbackreason) {
		this.thrbackreason = thrbackreason;
	}

	public String getBackremark() {
		return backremark;
	}

	public void setBackremark(String backremark) {
		this.backremark = backremark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGiftname() {
		return giftname;
	}

	public void setGiftname(String giftname) {
		this.giftname = giftname;
	}

	public String getIscarcheck() {
		return iscarcheck;
	}

	public void setIscarcheck(String iscarcheck) {
		this.iscarcheck = iscarcheck;
	}

	public String getQueryflag() {
		return queryflag;
	}

	public void setQueryflag(String queryflag) {
		this.queryflag = queryflag;
	}

}
