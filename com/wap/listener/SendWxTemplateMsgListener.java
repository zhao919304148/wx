package com.wap.listener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletInputStream;

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

public class SendWxTemplateMsgListener implements MessageListener {
	private static final Log logger = LogFactory.getLog(SendWxTemplateMsgListener.class);

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		try {
		String requestBody = null;
		String res = null;
		requestBody = new String(message.getBody(), "UTF-8");
		if (StringUtils.isNotBlank(requestBody)) {
			logger.info("队列接收到数据：" + requestBody);
			if (StringUtils.isNotBlank(requestBody)) {
				res = WeixinUtil.sendTemplateMessage(requestBody).toString();
			}
		}
		logger.info("微信返回报文："+res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("微信推送失败："+e.getMessage());
		}
	}

}
