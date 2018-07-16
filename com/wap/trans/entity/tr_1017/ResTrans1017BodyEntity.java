package com.wap.trans.entity.tr_1017;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1017BodyEntity {
	
	@XStreamAlias("EXCHANGE_LIST")
	private List<ResTrans1017ExchangeDataEntity> resTrans1017ExchangeDataEntityList;
	@XStreamAlias("CZ_LIST")
	private List<ResTrans1017CzDataEntity> resTrans1017CzDataEntityList;
	
	public List<ResTrans1017ExchangeDataEntity> getResTrans1017ExchangeDataEntityList() {
		return resTrans1017ExchangeDataEntityList;
	}
	public void setResTrans1017ExchangeDataEntityList(
			List<ResTrans1017ExchangeDataEntity> resTrans1017ExchangeDataEntityList) {
		this.resTrans1017ExchangeDataEntityList = resTrans1017ExchangeDataEntityList;
	}
	public List<ResTrans1017CzDataEntity> getResTrans1017CzDataEntityList() {
		return resTrans1017CzDataEntityList;
	}
	public void setResTrans1017CzDataEntityList(
			List<ResTrans1017CzDataEntity> resTrans1017CzDataEntityList) {
		this.resTrans1017CzDataEntityList = resTrans1017CzDataEntityList;
	}
}
