package com.wap.auth.exception;

import com.alibaba.fastjson.JSONObject;

/**
 * 
		
* 描述:<br>
* 用户认证异常<br>
* 约定：<br>
* 		用户登录错误：login_error_msg-->具体的登录错误信息<br>
* 		登录错误代码：login_error_code<br>
* 		-02	非注册用户<br>
* 		-03	未关联投保车辆信息<br>
* 		-04   参数错误：票据或签名不存在<br>
* 		-05   签名异常<br>
* 		-06 openId未取得
* 		-07  本次回话过期，请重新登录
* 		-08  检查总公司登录信息失败，需前往总公司登录页面进行重新登录
* 		-09 获取车辆信息失败，请稍后重试
* @author 许宝众
* @version 1.0
* @since 2017年6月19日 下午5:58:21
 */
public class AuthenticationException extends Exception {
	private static final long serialVersionUID = 1L;
	private String resCode;
	private String errMsg;
	
	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public AuthenticationException() {
	}
	/**
	 * 	用户登录错误：login_error_msg-->具体的登录错误信息<br>
	 * 	登录错误代码：login_error_code<br>
	 * 		-02	非注册用户<br>
	 * 		-03	未关联投保车辆信息<br>
	 * 		-04   参数错误：票据或签名不存在<br>
	 * 		-05   签名异常<br>
	 * 		-06 openId未取得
	 * 		-07  本次回话过期，请重新登录
	 * 		-08  检查总公司登录信息失败，需前往总公司登录页面进行重新登录
	 * 		-09 获取车辆信息失败，请稍后重试
	 */
	public AuthenticationException(String resCode,String errMsg){
		this.resCode = resCode;
		this.errMsg = errMsg;
	}
	public String tojson(){
		JSONObject js=new JSONObject();
		js.put("login_error_code", this.resCode);
		js.put("login_error_msg", this.errMsg);
		return js.toJSONString();
	}
}
