package com.wap.trans.entity.tr_1041;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;
import com.wap.trans.entity.tr_1040.ResTrans1040BodyEntity;

/**
 * 
		
		* 描述:
		*	生成洗车随机码
		* @author 骆利锋
		* @version 1.0
		* @since 2017年7月10日 下午2:25:00
 */
@XStreamAlias("PACKET")
public class ResTrans1041Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resHeadEntity;
	@XStreamAlias("BODY")
	private ResTrans1041BodyEntity resTrans1041BodyEntity;
	public ResponseHeadEntity getResHeadEntity() {
		return resHeadEntity;
	}
	public void setResHeadEntity(ResponseHeadEntity resHeadEntity) {
		this.resHeadEntity = resHeadEntity;
	}
	public ResTrans1041BodyEntity getResTrans1041BodyEntity() {
		return resTrans1041BodyEntity;
	}
	public void setResTrans1041BodyEntity(
			ResTrans1041BodyEntity resTrans1041BodyEntity) {
		this.resTrans1041BodyEntity = resTrans1041BodyEntity;
	}

}
