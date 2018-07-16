package com.wap.trans.entity.tr_1049;

import java.util.List;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.tr_1049.ResTrans1049ServiceDataEntity;

@XStreamAlias("BODY")
public class ResTrans1049BodyEntity {
	
	@XStreamAlias("SERVICE_LIST")
	private List<ResTrans1049ServiceDataEntity> resTrans1049ServiceDataList;

	public List<ResTrans1049ServiceDataEntity> getResTrans1049ServiceDataList() {
		return resTrans1049ServiceDataList;
	}

	public void setResTrans1049ServiceDataList(
			List<ResTrans1049ServiceDataEntity> resTrans1049ServiceDataList) {
		this.resTrans1049ServiceDataList = resTrans1049ServiceDataList;
	}
	
}
