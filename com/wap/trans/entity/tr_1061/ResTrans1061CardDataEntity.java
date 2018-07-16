package com.wap.trans.entity.tr_1061;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("RESERVE_ITEM")
public class ResTrans1061CardDataEntity {
	@XStreamAlias("SERVICEDATE")
	private String serviceDate;//服务时间
	
	@XStreamAlias("MAINTAINTYPE")
	private String mainTainType;//保养方式
	
	@XStreamAlias("FWSNAME")
	private String fwsName;//服务商
	
	@XStreamAlias("SERVICESTATE")
	private String serviceState;//服务状态
	
	@XStreamAlias("ORDERNO")
	private String orderNo;//订单号
	
	@XStreamAlias("ORDER_ITEM")
	private ResTrans1061CardOrderDataEntity resTrans1061CardOrderDataEntity;

	public String getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getMainTainType() {
		return mainTainType;
	}

	public void setMainTainType(String mainTainType) {
		this.mainTainType = mainTainType;
	}

	public String getFwsName() {
		return fwsName;
	}

	public void setFwsName(String fwsName) {
		this.fwsName = fwsName;
	}

	public String getServiceState() {
		return serviceState;
	}

	public void setServiceState(String serviceState) {
		this.serviceState = serviceState;
	}

	public ResTrans1061CardOrderDataEntity getResTrans1061CardOrderDataEntity() {
		return resTrans1061CardOrderDataEntity;
	}

	public void setResTrans1061CardOrderDataEntity(
			ResTrans1061CardOrderDataEntity resTrans1061CardOrderDataEntity) {
		this.resTrans1061CardOrderDataEntity = resTrans1061CardOrderDataEntity;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	
}
