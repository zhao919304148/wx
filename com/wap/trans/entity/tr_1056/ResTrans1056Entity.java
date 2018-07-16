package com.wap.trans.entity.tr_1056;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHead95518;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("ResponseVo")
public class ResTrans1056Entity {
	
	@XStreamAlias("responsehead")
	private ResponseHead95518 resuestHead95518;
	
	@XStreamAlias("responseBodyVo")
	private ResTrans1056BodyEntity resTrans1056BodyEntity;
	
	public ResponseHead95518 getResuestHead95518() {
		return resuestHead95518;
	}

	public void setResuestHead95518(ResponseHead95518 resuestHead95518) {
		this.resuestHead95518 = resuestHead95518;
	}

	public ResTrans1056BodyEntity getResTrans1056BodyEntity() {
		return resTrans1056BodyEntity;
	}

	public void setResTrans1056BodyEntity(
			ResTrans1056BodyEntity resTrans1056BodyEntity) {
		this.resTrans1056BodyEntity = resTrans1056BodyEntity;
	}
	
}
