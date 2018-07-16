package com.wap.trans.entity.tr_1018;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CZ_DATA")
public class ResTrans1018CzDataEntity {
	
	@XStreamAlias("CZHREFTITLE")
	private String czhrefTitle;// 充值链接标题
	@XStreamAlias("CZHREF")
	private String czhref;// 充值链接
	@XStreamAlias("CZAMOUNT")
	private String czamount;// 充值金额
	@XStreamAlias("CZDATE")
	private String czdate;// 充值日期
	
	public String getCzhrefTitle() {
		return czhrefTitle;
	}
	public void setCzhrefTitle(String czhrefTitle) {
		this.czhrefTitle = czhrefTitle;
	}
	public String getCzhref() {
		return czhref;
	}
	public void setCzhref(String czhref) {
		this.czhref = czhref;
	}
	public String getCzamount() {
		return czamount;
	}
	public void setCzamount(String czamount) {
		this.czamount = czamount;
	}
	public String getCzdate() {
		return czdate;
	}
	public void setCzdate(String czdate) {
		this.czdate = czdate;
	}
}
