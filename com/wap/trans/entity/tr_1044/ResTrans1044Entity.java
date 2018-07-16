package com.wap.trans.entity.tr_1044;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

/**
 * 
		
		* 描述:洗车记录查询
		*
		* @author 骆利锋
		* @version 1.0
		* @since 2017年6月5日 上午9:22:03
 */
@XStreamAlias("PACKET")
public class ResTrans1044Entity {
	
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1044BodyEntity resTrans1044BodyEntity;

	public ResTrans1044BodyEntity getResTrans1044BodyEntity() {
		return resTrans1044BodyEntity;
	}

	public void setResTrans1044BodyEntity(
			ResTrans1044BodyEntity resTrans1044BodyEntity) {
		this.resTrans1044BodyEntity = resTrans1044BodyEntity;
	}

	public ResponseHeadEntity getResHeadEntity() {
		return resHeadEntity;
	}

	public void setResHeadEntity(ResponseHeadEntity resHeadEntity) {
		this.resHeadEntity = resHeadEntity;
	}
	
}
