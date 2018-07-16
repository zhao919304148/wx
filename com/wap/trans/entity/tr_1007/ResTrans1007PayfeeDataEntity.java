package com.wap.trans.entity.tr_1007;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("PAYFEE_DATA")
public class ResTrans1007PayfeeDataEntity {
	@XStreamAlias("LSYCODE")
	private String lsycode;// 理算员代码
	@XStreamAlias("LSYNAME")
	private String lsyname;// 理算员名称
	@XStreamAlias("LSYMOBILE")
	private String lsymobile;// 理算员电话
	@XStreamAlias("PAYDATE")
	private String paydate;// 支付时间
	@XStreamAlias("PAYSTATE")
	private String paystate;// 支付状态
	@XStreamAlias("SUMREALPAY")
	private String sumrealpay;// 支付金额

	public String getLsycode() {
		return lsycode;
	}

	public void setLsycode(String lsycode) {
		this.lsycode = lsycode;
	}

	public String getLsyname() {
		return lsyname;
	}

	public void setLsyname(String lsyname) {
		this.lsyname = lsyname;
	}

	public String getLsymobile() {
		return lsymobile;
	}

	public void setLsymobile(String lsymobile) {
		this.lsymobile = lsymobile;
	}

	public String getPaydate() {
		return paydate;
	}

	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}

	public String getPaystate() {
		return paystate;
	}

	public void setPaystate(String paystate) {
		this.paystate = paystate;
	}

	public String getSumrealpay() {
		return sumrealpay;
	}

	public void setSumrealpay(String sumrealpay) {
		this.sumrealpay = sumrealpay;
	}

}
