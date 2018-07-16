package com.wap.trans.entity.tr_1018;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1018BodyEntity {
	
	@XStreamAlias("EXCHANGE_LIST")
	private List<ResTrans1018ExchangeDataEntity> resTrans1018ExchangeDataEntityList;
	@XStreamAlias("CZ_LIST")
	private List<ResTrans1018CzDataEntity> resTrans1018CzDataEntityList;
	
	public List<ResTrans1018ExchangeDataEntity> getResTrans1018ExchangeDataEntityList() {
		return resTrans1018ExchangeDataEntityList;
	}
	public void setResTrans1018ExchangeDataEntityList(
			List<ResTrans1018ExchangeDataEntity> resTrans1018ExchangeDataEntityList) {
		this.resTrans1018ExchangeDataEntityList = resTrans1018ExchangeDataEntityList;
	}
	public List<ResTrans1018CzDataEntity> getResTrans1018CzDataEntityList() {
		return resTrans1018CzDataEntityList;
	}
	public void setResTrans1018CzDataEntityList(
			List<ResTrans1018CzDataEntity> resTrans1018CzDataEntityList) {
		this.resTrans1018CzDataEntityList = resTrans1018CzDataEntityList;
	}
}
