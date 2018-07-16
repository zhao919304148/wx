package com.wap.trans.entity.tr_1038;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.tr_1021.ResTrans1021BasePartEntity;

@XStreamAlias("BODY")
public class ResTrans1038BodyEntity {
	
	@XStreamAlias("BASE_PART")
	private ResTrans1038BasePartEntity   resTrans1038BasePartEntity;
	
	@XStreamAlias("CAR_LIST")
	private List<ResTrans1038CarDataEntity> resTrans1038CarDataEntityList;

	public List<ResTrans1038CarDataEntity> getResTrans1038CarDataEntityList() {
		return resTrans1038CarDataEntityList;
	}

	public void setResTrans1038CarDataEntityList(
			List<ResTrans1038CarDataEntity> resTrans1038CarDataEntityList) {
		this.resTrans1038CarDataEntityList = resTrans1038CarDataEntityList;
	}
	
}
