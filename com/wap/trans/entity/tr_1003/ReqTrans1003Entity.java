package com.wap.trans.entity.tr_1003;

public class ReqTrans1003Entity {
	private String orderno; // 订单号
	private String barcode; // 条码
	private String queryflag; // 查询标识 1:一次配送 2:二次配送

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getQueryflag() {
		return queryflag;
	}

	public void setQueryflag(String queryflag) {
		this.queryflag = queryflag;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

}
