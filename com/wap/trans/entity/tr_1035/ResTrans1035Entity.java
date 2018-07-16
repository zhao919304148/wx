package com.wap.trans.entity.tr_1035;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

/**
 * 
		
		* 描述:关联投保车辆，绑定（提交）用户信息
		*
		* @author 骆利锋
		* @version 1.0
		* @since 2017年6月5日 上午8:45:19
 */
@XStreamAlias("PACKET")
public class ResTrans1035Entity {
	
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resHeadEntity;

	public ResponseHeadEntity getResHeadEntity() {
		return resHeadEntity;
	}

	public void setResHeadEntity(ResponseHeadEntity resHeadEntity) {
		this.resHeadEntity = resHeadEntity;
	}
	
}
