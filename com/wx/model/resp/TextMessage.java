package com.wx.model.resp;


/**
 * 文本消息
 * 
 * @author huzhonggan
 * @date 2013-12-05
 */
public class TextMessage extends BaseMessage {
	// 回复的消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
