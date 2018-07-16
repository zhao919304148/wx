package com.wap.trans.entity.tr_1059;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1059BodyEntity {
	
	@XStreamAlias("BASE_PART")
	private ResTrans1059BasePartEntity resTrans1059BasePartEntity;
	
	@XStreamAlias("RECORD_LIST")
	private List<ResTrans1059RecordListEntity> resTrans1059RecordListEntityList;

	public ResTrans1059BasePartEntity getResTrans1059BasePartEntity() {
		return resTrans1059BasePartEntity;
	}

	public void setResTrans1059BasePartEntity(
			ResTrans1059BasePartEntity resTrans1059BasePartEntity) {
		this.resTrans1059BasePartEntity = resTrans1059BasePartEntity;
	}

	public List<ResTrans1059RecordListEntity> getResTrans1059RecordListEntityList() {
		return resTrans1059RecordListEntityList;
	}

	public void setResTrans1059RecordListEntityList(
			List<ResTrans1059RecordListEntity> resTrans1059RecordListEntityList) {
		this.resTrans1059RecordListEntityList = resTrans1059RecordListEntityList;
	}

	
	
}
