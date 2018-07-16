package com.wap.trans.entity.tr_1039;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

/**
 * 
		
		* 描述:
		*	车辆解绑
		* @author 许宝众
		* @version 1.0
		* @since 2017年6月27日 下午2:25:00
 */
@XStreamAlias("PACKET")
public class ResTrans1039Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resHeadEntity;

	public ResponseHeadEntity getResHeadEntity() {
		return resHeadEntity;
	}

	public void setResHeadEntity(ResponseHeadEntity resHeadEntity) {
		this.resHeadEntity = resHeadEntity;
	}
}
