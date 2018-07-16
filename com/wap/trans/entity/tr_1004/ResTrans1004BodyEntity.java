package com.wap.trans.entity.tr_1004;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1004BodyEntity {
	@XStreamAlias("ORDER_INFO")
	private ResTrans1004OrderInfoEntity resTrans1004OrderInfoEntity;

	public ResTrans1004OrderInfoEntity getResTrans1004OrderInfoEntity() {
		return resTrans1004OrderInfoEntity;
	}

	public void setResTrans1004OrderInfoEntity(
			ResTrans1004OrderInfoEntity resTrans1004OrderInfoEntity) {
		this.resTrans1004OrderInfoEntity = resTrans1004OrderInfoEntity;
	}

}
