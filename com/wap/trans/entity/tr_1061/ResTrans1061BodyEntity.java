package com.wap.trans.entity.tr_1061;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.tr_1061.ResTrans1061CardDataEntity;

public class ResTrans1061BodyEntity {
    
	@XStreamAlias("RESERVE_LIST")
	private List<ResTrans1061CardDataEntity> resTrans1061CardDataEntityList;

	public List<ResTrans1061CardDataEntity> getResTrans1061CardDataEntityList() {
		return resTrans1061CardDataEntityList;
	}

	public void setResTrans1061CardDataEntityList(
			List<ResTrans1061CardDataEntity> resTrans1061CardDataEntityList) {
		this.resTrans1061CardDataEntityList = resTrans1061CardDataEntityList;
	}
	
}
