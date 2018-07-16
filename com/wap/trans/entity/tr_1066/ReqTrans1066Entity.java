package com.wap.trans.entity.tr_1066;
/**
 * 
		
		* 描述:
		*	微信外部接口统一入口请求Vo
		* @author 许宝众
		* @version 1.0
		* @since 2018年2月9日 上午9:22:32
 */
public class ReqTrans1066Entity {
	/**请求报文**/
	private String reqMsg;
	/**请求报文格式，支持json或者xml**/
	private String contentType;
	public String getReqMsg() {
		return reqMsg;
	}
	public void setReqMsg(String reqMsg) {
		this.reqMsg = reqMsg;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
