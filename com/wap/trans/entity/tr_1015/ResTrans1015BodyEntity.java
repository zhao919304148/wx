package com.wap.trans.entity.tr_1015;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1015BodyEntity {
	@XStreamAlias("NETWORK_LIST")
	private List<ResTrans1015NetWorkEntity> resTrans1015NetWorkEntityList;

	public List<ResTrans1015NetWorkEntity> getResTrans1015NetWorkEntityList() {
		return resTrans1015NetWorkEntityList;
	}

	public void setResTrans1015NetWorkEntityList(
			List<ResTrans1015NetWorkEntity> resTrans1015NetWorkEntityList) {
		this.resTrans1015NetWorkEntityList = resTrans1015NetWorkEntityList;
	}

}
