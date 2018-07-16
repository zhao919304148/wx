package com.wap.trans.entity.tr_1040;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BASE_PART")
public class ResTrans1040BasePartEntity {

	@XStreamAlias("NETWORK_LIST")
	private List<ResTrans1040NetworkDataEntity> resTrans1040NetworkDataEntityList;

	public List<ResTrans1040NetworkDataEntity> getResTrans1040NetworkDataEntityList() {
		return resTrans1040NetworkDataEntityList;
	}

	public void setResTrans1040NetworkDataEntityList(
			List<ResTrans1040NetworkDataEntity> resTrans1040NetworkDataEntityList) {
		this.resTrans1040NetworkDataEntityList = resTrans1040NetworkDataEntityList;
	}
	
}
