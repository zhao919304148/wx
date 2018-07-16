package com.wap.trans.entity.tr_1038;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

/**
 * 
		
		* 描述:电商福袋登陆，获取用户基本信息
		*
		* @author 骆利锋
		* @version 1.0
		* @since 2017年6月5日 上午9:22:03
 */
@XStreamAlias("PACKET")
public class ResTrans1038Entity {
	
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1038BodyEntity resTrans1038BodyEntity;

	public ResTrans1038BodyEntity getResTrans1038BodyEntity() {
		return resTrans1038BodyEntity;
	}

	public void setResTrans1038BodyEntity(
			ResTrans1038BodyEntity resTrans1038BodyEntity) {
		this.resTrans1038BodyEntity = resTrans1038BodyEntity;
	}

	public ResponseHeadEntity getResHeadEntity() {
		return resHeadEntity;
	}

	public void setResHeadEntity(ResponseHeadEntity resHeadEntity) {
		this.resHeadEntity = resHeadEntity;
	}
	
}
