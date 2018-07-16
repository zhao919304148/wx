package com.wx.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpRequestUtil {
	
	private static Logger log = Logger.getLogger(HttpRequestUtil.class);
	public static String sendHttpPostRequest(String reqURL, String data) {
		HttpClient httpclient = new DefaultHttpClient();
		String respStr = "";
		try {
			log.info("Wx HttpRequestUtil reqStr:" + data);
			HttpPost httppost = new HttpPost(reqURL);
			StringEntity strEntity = new StringEntity(data, "UTF-8");
			strEntity.setContentType("application/x-www-form-urlencoded");
			httppost.setEntity(strEntity);
			log.info(EntityUtils.toString(strEntity));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

			log.info(response.getStatusLine());
			if (resEntity != null) {
				log.info("resEntity contentLength:" + resEntity.getContentLength());
				respStr = EntityUtils.toString(resEntity);
			}
			EntityUtils.consume(resEntity);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		log.info("Wx HttpRequestUtil resStr:" + respStr);
		return respStr;
	}
	public static String filter(String data) {
		return data.replace("\r\n", "%0D%0A").replace("+", "%2B");
	}
}