package com.wap.trans.entity.tr_1016;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1016BodyEntity {
	
	@XStreamAlias("POLICY_LIST")
	private List<ResTrans1016PolicyDataEntity> resTrans1016PolicyDataEntityList;

	public List<ResTrans1016PolicyDataEntity> getResTrans1016PolicyDataEntityList() {
		return resTrans1016PolicyDataEntityList;
	}

	public void setResTrans1016PolicyDataEntityList(
			List<ResTrans1016PolicyDataEntity> resTrans1016PolicyDataEntityList) {
		this.resTrans1016PolicyDataEntityList = resTrans1016PolicyDataEntityList;
	}
}
