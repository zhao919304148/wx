package com.wap.trans.entity.tr_1033;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

/**
 * 
		
		* 描述:关联投保人车辆，获取投保人手机号
		*
		* @author 骆利锋
		* @version 1.0
		* @since 2017年6月2日 上午9:22:03
 */
@XStreamAlias("PACKET")
public class ResTrans1033Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resHeadEntity;
	
	@XStreamAlias("BODY")
	private ResTrans1033BodyEntity resTrans1033BodyEntity;

	public ResTrans1033BodyEntity getResTrans1033BodyEntity() {
		return resTrans1033BodyEntity;
	}

	public void setResTrans1033BodyEntity(
			ResTrans1033BodyEntity resTrans1033BodyEntity) {
		this.resTrans1033BodyEntity = resTrans1033BodyEntity;
	}

	public ResponseHeadEntity getResHeadEntity() {
		return resHeadEntity;
	}

	public void setResHeadEntity(ResponseHeadEntity resHeadEntity) {
		this.resHeadEntity = resHeadEntity;
	}
	
}
