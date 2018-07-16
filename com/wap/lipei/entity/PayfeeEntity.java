package com.wap.lipei.entity;

import com.sys.dic.SysDicHelper;

/**
 * 
		
		* 描述:支付赔款信息实体类
		*
		* @author 朱久满
		* @version 1.0
		* @since 2015年12月29日 下午4:55:25
 */
public class PayfeeEntity {
	private String lsycode;// 理算员代码
	private String lsyname;// 理算员名称
	private String lsymobile;// 理算员电话
	private String paydate;// 支付时间
	private String paystate;// 支付状态
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

	public String getPaystatename() {
		return SysDicHelper.getInstance().getValueByDicTypeAndDicId("PAYSTATE", this.paystate);
	}
}
