package com.wx.model.req;


/**
 * ��Ƶ��Ϣ
 * 
 * @author huzhonggan
 * @date 2013-12-05
 */
public class VoiceMessage extends BaseMessage {
	// ý��ID
	private String MediaId;
	// ������ʽ
	private String Format;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}
}

