package com.wap.trans.entity.tr_1049;

import java.util.List;
import com.wap.trans.entity.tr_1049.ReqTrans1049CarDataEntity;

public class ReqTrans1049Entity {
	
	private String querytype;											//查询类型
	private List<ReqTrans1049CarDataEntity> reqTrans1049CarDataList;	//车辆列表
	
	public String getQuerytype() {
		return querytype;
	}
	public void setQuerytype(String querytype) {
		this.querytype = querytype;
	}
	public List<ReqTrans1049CarDataEntity> getReqTrans1049CarDataList() {
		return reqTrans1049CarDataList;
	}
	public void setReqTrans1049CarDataList(
			List<ReqTrans1049CarDataEntity> reqTrans1049CarDataList) {
		this.reqTrans1049CarDataList = reqTrans1049CarDataList;
	}

}
