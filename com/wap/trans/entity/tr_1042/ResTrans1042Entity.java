package com.wap.trans.entity.tr_1042;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.wap.trans.entity.ResponseHeadEntity;

/**
 * 
		
		* 描述:
		*	校验洗车码 返回报文
		* @author 许宝众
		* @version 1.0
		* @since 2017年7月5日 上午11:15:27
 */
@XStreamAlias("PACKET")
public class ResTrans1042Entity {
	@XStreamAlias("HEAD")
	private ResponseHeadEntity resHeadEntity;

	public ResponseHeadEntity getResHeadEntity() {
		return resHeadEntity;
	}

	public void setResHeadEntity(ResponseHeadEntity resHeadEntity) {
		this.resHeadEntity = resHeadEntity;
	}
}
