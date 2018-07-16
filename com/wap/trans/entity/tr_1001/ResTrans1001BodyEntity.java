package com.wap.trans.entity.tr_1001;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1001BodyEntity {
	@XStreamAlias("USER_INFO")
	private ResTrans1001UserInfoEntity resTrans1001UserInfoEntity;

	public ResTrans1001UserInfoEntity getResTrans1001UserInfoEntity() {
		return resTrans1001UserInfoEntity;
	}

	public void setResTrans1001UserInfoEntity(
			ResTrans1001UserInfoEntity resTrans1001UserInfoEntity) {
		this.resTrans1001UserInfoEntity = resTrans1001UserInfoEntity;
	}

}
