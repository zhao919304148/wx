package com.wap.trans.entity.tr_1037;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

/**
 * 
		
		* 描述:投保手机号变更（提交资料审核）
		*
		* @author 骆利锋
		* @version 1.0
		* @since 2017年6月5日 上午9:22:03
 */
@XStreamAlias("PACKET")
public class ResTrans1037Entity {
	
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resHeadEntity;

	public ResponseHeadEntity getResHeadEntity() {
		return resHeadEntity;
	}

	public void setResHeadEntity(ResponseHeadEntity resHeadEntity) {
		this.resHeadEntity = resHeadEntity;
	}
	
}
