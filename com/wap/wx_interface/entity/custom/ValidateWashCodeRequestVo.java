package com.wap.wx_interface.entity.custom;

/**
 * 
		
		* 描述:
		*	校验洗车码的外部请求接收Vo
		* @author 许宝众
		* @version 1.0
		* @since 2017年7月11日 上午10:15:21
 */
public class ValidateWashCodeRequestVo {
	private String account;
	private String message;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
