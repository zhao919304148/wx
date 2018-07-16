package com.wap.trans.entity.tr_1053;

import java.util.List;


public class ReqTrans1053Entity {
	//预约服务类型   01：代驾预约    02：代办验车    03：代办保养    04：代办理赔
	private String operatetype;
	private ReqTrans1053YuyueDataEntity reqTrans1053YuyueDataEntity;	//预约信息列表
	
	public String getOperatetype() {
		return operatetype;
	}
	public void setOperatetype(String operatetype) {
		this.operatetype = operatetype;
	}
	public ReqTrans1053YuyueDataEntity getReqTrans1053YuyueDataEntity() {
		return reqTrans1053YuyueDataEntity;
	}
	public void setReqTrans1053YuyueDataEntity(
			ReqTrans1053YuyueDataEntity reqTrans1053YuyueDataEntity) {
		this.reqTrans1053YuyueDataEntity = reqTrans1053YuyueDataEntity;
	}
    
    
}
