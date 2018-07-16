package com.wap.trans.entity.tr_1054;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHead95518;
import com.wap.trans.entity.ResponseHeadEntity;

@XStreamAlias("ResponseVo")
public class ResTrans1054Entity {
	
	@XStreamAlias("responsehead")
	private ResponseHead95518 responseHead95518;
	
	@XStreamAlias("responseBodyVo")
	private ResTrans1054BodyEntity resTrans1054BodyEntity;
	
	public ResponseHead95518 getResponseHead95518() {
		return responseHead95518;
	}

	public void setResponseHead95518(ResponseHead95518 responseHead95518) {
		this.responseHead95518 = responseHead95518;
	}

	public ResTrans1054BodyEntity getResTrans1054BodyEntity() {
		return resTrans1054BodyEntity;
	}

	public void setResTrans1054BodyEntity(
			ResTrans1054BodyEntity resTrans1054BodyEntity) {
		this.resTrans1054BodyEntity = resTrans1054BodyEntity;
	}
	
}
