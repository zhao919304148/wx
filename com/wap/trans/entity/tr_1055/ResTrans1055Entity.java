package com.wap.trans.entity.tr_1055;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHead95518;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("ResponseVo")
public class ResTrans1055Entity {

	@XStreamAlias("responsehead")
	private ResponseHead95518 resuestHead95518;
	
	@XStreamAlias("responseBodyVo")
	private ResTrans1055BodyEntity resTrans1055BodyEntity;

	public ResponseHead95518 getResuestHead95518() {
		return resuestHead95518;
	}

	public void setResuestHead95518(ResponseHead95518 resuestHead95518) {
		this.resuestHead95518 = resuestHead95518;
	}

	public ResTrans1055BodyEntity getResTrans1055BodyEntity() {
		return resTrans1055BodyEntity;
	}

	public void setResTrans1055BodyEntity(
			ResTrans1055BodyEntity resTrans1055BodyEntity) {
		this.resTrans1055BodyEntity = resTrans1055BodyEntity;
	}
	
}
