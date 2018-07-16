package com.wap.trans.entity.tr_1034;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

/**
 * 
		
		* 描述:获取手机验证码
		*
		* @author 骆利锋
		* @version 1.0
		* @since 2017年6月2日 下午4:28:42
 */
@XStreamAlias("PACKET")
public class ResTrans1034Entity {
	
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resHeadEntity;

	public ResponseHeadEntity getResHeadEntity() {
		return resHeadEntity;
	}

	public void setResHeadEntity(ResponseHeadEntity resHeadEntity) {
		this.resHeadEntity = resHeadEntity;
	}
	
}
