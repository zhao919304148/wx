package com.wap.kfcenter.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wap.util.CommonUtils;
import com.wx.util.WeixinUtil;

@Controller
@RequestMapping(value={"/reglic"})
public class RegistLicenseController {
	
	private static final Log logger = LogFactory.getLog(RegistLicenseController.class);

	@RequestMapping(value="/turn.do")
	@ResponseBody
	public String getClientService(HttpServletRequest request,HttpServletResponse response) {
		
		Map<String, String> returnmap = null;
		String backStr = "";
		String openId = request.getParameter("openId");
		logger.info("接入人工客服openId：" +openId);
		String checkOpenCode = "";
		net.sf.json.JSONObject json = null;
		try {
			JSONObject openJson = CommonUtils.checkWxOpen(request, openId);
			checkOpenCode = (String)openJson.get("resCode");
			if(!"0".equals(checkOpenCode)){
				backStr = "2";
				return backStr;
			}
			if (StringUtils.isNotEmpty(openId)) {
				//人工客服接入
				returnmap = WeixinUtil.geGetTalkSessionFromPICC(openId);
				if(returnmap != null){
					backStr = returnmap.get("retcode");
					if("0".equals(backStr)){
						//接入人工客服成功，推送一条消息
						Map<String, Object> jsonmap = new HashMap<String, Object>();
						Map<String, Object> textmap = new HashMap<String, Object>();
						textmap.put("content", "欢迎您选择\"北京人保财险\"人工客服,请稍后~~");
						jsonmap.put("touser", openId);
						jsonmap.put("msgtype", "text");
						jsonmap.put("text", textmap);
						json = WeixinUtil.sendCustomMessage(net.sf.json.JSONObject.fromObject(jsonmap).toString());						
					}
				}else{
					backStr = "1";
				}
			}else{
				backStr = "1";
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return backStr;
	}
}
