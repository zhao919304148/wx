package com.wap.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sys.dic.SysDicHelper;
import com.wx.model.pojo.AccessToken;
import com.wx.util.WeixinUtil;
@Service("wxSDKUtil")
public class WxSDKUtil {
	private static final Log logger = LogFactory.getLog(WxSDKUtil.class);
	private static  AccessToken access_token = null;
	private static  String appId = null;
	private static  String jsapi_ticket = null;
	
	/**
	 * 唯一实例
	 */
	private static final WxSDKUtil INSTANCE = new WxSDKUtil();

	/**
	 * 防止外部构造
	 *
	 */
	private WxSDKUtil(){
	}
	/**
	 * 获取唯一实例 wangjinhua 2015-12-10 15:12:21
	 * @return
	 */
	public static final WxSDKUtil getInstance(){
			INSTANCE.init();
		return WxSDKUtil.INSTANCE;
	}
	public  void init(){
//		System.out.println("------>获取微信接口凭据");
//		access_token = WeixinUtil.getAccessToken(appId, appsecret);
//		if(null != access_token){
//			jsapi_ticket = WeixinUtil.getJsapi_ticket(access_token.getToken()).get("ticket");
//		}
		 appId = SysDicHelper.getInstance().getValueByDicTypeAndDicId("WEIXINUTIL", "APPID");;
		//获取微信接口凭据 从PICC服务器
		jsapi_ticket = WeixinUtil.getJsapi_ticket_fromPICC().get("jsapi_ticket");
	}
	public Map<String, String> sign(String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appId", appId);
        logger.error("url:"+url);
        logger.error("nonceStr:"+nonce_str);
        logger.error("timestamp:"+timestamp);
        logger.error("signature:"+signature);
        logger.error("appId:"+appId);
        logger.error("jsapi_ticket:"+jsapi_ticket);
        return ret;
    }

    private String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
    
	public AccessToken getAccess_token() {
		return access_token;
	}
	public String getAppId() {
		return appId;
	}
	public String getJsapi_ticket() {
		return jsapi_ticket;
	}
}
