package com.wap.trans.entity.tr_1040;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

/**
 * 
		
		* 描述:洗车网点查询
		*
		* @author 骆利锋
		* @version 1.0
		* @since 2017年6月5日 上午9:22:03
 */
@XStreamAlias("PACKET")
public class ResTrans1040Entity {
	
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1040BodyEntity resTrans1040BodyEntity;

	public ResTrans1040BodyEntity getResTrans1040BodyEntity() {
		return resTrans1040BodyEntity;
	}

	public void setResTrans1040BodyEntity(
			ResTrans1040BodyEntity resTrans1040BodyEntity) {
		this.resTrans1040BodyEntity = resTrans1040BodyEntity;
	}

	public ResponseHeadEntity getResHeadEntity() {
		return resHeadEntity;
	}

	public void setResHeadEntity(ResponseHeadEntity resHeadEntity) {
		this.resHeadEntity = resHeadEntity;
	}
	
}
