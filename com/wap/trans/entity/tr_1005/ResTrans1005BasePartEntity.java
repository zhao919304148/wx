package com.wap.trans.entity.tr_1005;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BASE_PART")
public class ResTrans1005BasePartEntity {
	@XStreamAlias("ORDERNO")
	private String orderno;// 订单号
	@XStreamAlias("USERCODE")
	private String usercode;// 用户代码

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

}
