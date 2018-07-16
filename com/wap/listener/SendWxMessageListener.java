package com.wap.listener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletInputStream;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.wap.util.AESCode;
import com.wap.wx_interface.controller.WxController;
import com.wx.util.WeixinUtil;
public class SendWxMessageListener implements MessageListener {
	
	private static final Log logger = LogFactory.getLog(SendWxTemplateMsgListener.class);

	@Override
	public void onMessage(Message message) {
		Throwable ex = null;
		String requestBody = null;
		String res = null;
		try {
			requestBody = new String(message.getBody(), "UTF-8");
			if (StringUtils.isNotBlank(requestBody)) {
				if (StringUtils.isNotBlank(requestBody)) {
					JSONObject json = JSONObject.fromObject(requestBody);
					Map<String, Object> jsonmap = new HashMap<String, Object>();
					Map<String, Object> textmap = new HashMap<String, Object>();
					textmap.put("content", json.get("content"));
					jsonmap.put("touser", json.get("openid"));
					jsonmap.put("msgtype", "text");
					jsonmap.put("text", textmap);
					res = WeixinUtil.sendCustomMessage(JSONObject.fromObject(jsonmap)
							.toString()).toString();
				}
			}
		} catch (Exception e) {
			ex = e;
		}finally{
			logger.info("[接收到队列消息："+requestBody+"]-[响应数据："+res+"]-异常：",ex);
//			if(ex!=null){
//				throw new RuntimeException(ex);
//			}
		}
	}

	

}
