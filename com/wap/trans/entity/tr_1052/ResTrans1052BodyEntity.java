package com.wap.trans.entity.tr_1052;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;


public class ResTrans1052BodyEntity {
	
	@XStreamAlias("CUSSERVICE_LIST")
	private List<ResTrans1052CusserviceDataEntity> resTrans1052CusserviceDataList;

	public List<ResTrans1052CusserviceDataEntity> getResTrans1052CusserviceDataList() {
		return resTrans1052CusserviceDataList;
	}

	public void setResTrans1052CusserviceDataList(
			List<ResTrans1052CusserviceDataEntity> resTrans1052CusserviceDataList) {
		this.resTrans1052CusserviceDataList = resTrans1052CusserviceDataList;
	}

}
