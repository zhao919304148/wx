package com.wap.trans.entity.tr_1052;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CUSSERVICE_DATA")
public class ResTrans1052CusserviceDataEntity {
	//�ͻ�ID
	@XStreamAlias("CUSID")
	private String cusid;
	//�ͻ�����
	@XStreamAlias("CUSTOMERCNAME")
	private String customercname;
	//�������
	@XStreamAlias("SERVICECODE")
	private String servicecode;
	//��������
	@XStreamAlias("SERVICENAME")
	private String servicename;
	//��������
	@XStreamAlias("SERVICETYPE")
	private String servicetype;
	//�ͻ�����ID
	@XStreamAlias("CSERID")
	private String cserid;
	//����
	@XStreamAlias("TOTALSTOCK")
	private String totalstock;
	//ʣ����
	@XStreamAlias("REMAINSTOCK")
	private String remainstock;
	//����ʱ��
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
