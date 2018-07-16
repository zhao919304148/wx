package com.wap.trans.entity.tr_1052;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CUSSERVICE_DATA")
public class ResTrans1052CusserviceDataEntity {
	//客户ID
	@XStreamAlias("CUSID")
	private String cusid;
	//客户姓名
	@XStreamAlias("CUSTOMERCNAME")
	private String customercname;
	//服务代码
	@XStreamAlias("SERVICECODE")
	private String servicecode;
	//服务名称
	@XStreamAlias("SERVICENAME")
	private String servicename;
	//服务类型
	@XStreamAlias("SERVICETYPE")
	private String servicetype;
	//客户服务ID
	@XStreamAlias("CSERID")
	private String cserid;
	//总数
	@XStreamAlias("TOTALSTOCK")
	private String totalstock;
	//剩余数
	@XStreamAlias("REMAINSTOCK")
	private String remainstock;
	//到期时间
	@XStreamAlias("ENDDATE")
	private String enddate;

	public String getCusid() {
		return cusid;
	}

	public void setCusid(String cusid) {
		this.cusid = cusid;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public String getTotalstock() {
		return totalstock;
	}

	public void setTotalstock(String totalstock) {
		this.totalstock = totalstock;
	}

	public String getRemainstock() {
		return remainstock;
	}

	public void setRemainstock(String remainstock) {
		this.remainstock = remainstock;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getCustomercname() {
		return customercname;
	}

	public void setCustomercname(String customercname) {
		this.customercname = customercname;
	}

	public String getServicecode() {
		return servicecode;
	}

	public void setServicecode(String servicecode) {
		this.servicecode = servicecode;
	}

	public String getServicetype() {
		return servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}

	public String getCserid() {
		return cserid;
	}

	public void setCserid(String cserid) {
		this.cserid = cserid;
	}
	
	
}
