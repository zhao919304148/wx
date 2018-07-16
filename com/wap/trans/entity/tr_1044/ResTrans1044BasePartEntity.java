package com.wap.trans.entity.tr_1044;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BASE_PART")
public class ResTrans1044BasePartEntity {

	@XStreamAlias("DATA_LIST")
	private List<ResTrans1044RecordDataEntity> resTrans1044RecordDataEntityList;

	public List<ResTrans1044RecordDataEntity> getResTrans1044RecordDataEntityList() {
		return resTrans1044RecordDataEntityList;
	}

	public void setResTrans1044RecordDataEntityList(
			List<ResTrans1044RecordDataEntity> resTrans1044RecordDataEntityList) {
		this.resTrans1044RecordDataEntityList = resTrans1044RecordDataEntityList;
	}

}
