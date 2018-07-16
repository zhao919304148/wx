package com.wap.trans.entity.tr_1036;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

/**
 * 
		
		* 描述:投保手机号变更（原手机号可用）
		*
		* @author 骆利锋
		* @version 1.0
		* @since 2017年6月5日 上午9:12:30
 */
@XStreamAlias("PACKET")
public class ResTrans1036Entity {
	
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resHeadEntity;

	public ResponseHeadEntity getResHeadEntity() {
		return resHeadEntity;
	}

	public void setResHeadEntity(ResponseHeadEntity resHeadEntity) {
		this.resHeadEntity = resHeadEntity;
	}
	
}
