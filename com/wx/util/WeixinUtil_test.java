package com.wx.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.ezmorph.Morpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sys.dic.SysDicHelper;
import com.wx.model.pojo.AccessToken;
import com.wx.model.pojo.Menu;


/**
 * 公众平台通用接口工具类
 * 
 * @author 胡忠淦
 * @date 2013-12-09
 */
public class WeixinUtil_test {

	
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	//private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);  
	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	//二维码创建post
	public static String qrcode_create_url="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
	
	//二维码创建post
	public static String jsapi_ticket_url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	
	//下载媒体文件地址get
	public static String file_get_url="http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	
	
	private static final Log logger = LogFactory.getLog(WeixinUtil_test.class);
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			System.out.println(buffer.toString());
			String data=buffer.toString();
			jsonObject = JSONObject.fromObject(data);
		} catch (ConnectException ce) {
			System.out.println("Weixin server connection timed out.");
			System.out.println(ce);
		} catch (Exception e) {
			System.out.println("https request error");
			System.out.println(e);
		}
		return jsonObject;
	}
	public static JSONObject httpRequestPICC(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection)url.openConnection();
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
			if ("POST".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			System.out.println(buffer.toString());
			String data=buffer.toString();
			jsonObject = JSONObject.fromObject(data);
		} catch (ConnectException ce) {
			System.out.println("Weixin server connection timed out.");
			System.out.println(ce);
		} catch (Exception e) {
			System.out.println("https request error");
			System.out.println(e);
		}
		return jsonObject;
	}
	

	/**
	 * 获取access_token
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (Exception e) {
				accessToken = null;
				// 获取token失败
				logger.error("获取token失败 errcode:"+ jsonObject.getInt("errcode")+" errmsg:"+ jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}
	
	/**
	 * 获取jsapi_ticket
	 * 
	 * @param access_token
	 * @return
	 */
	public static Map<String, String> getJsapi_ticket(String access_token) {
		Map<String, String> map = new HashMap<String, String>();
		String requestUrl = jsapi_ticket_url.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				map.put("ticket", jsonObject.getString("ticket"));
				map.put("expires_in", jsonObject.getInt("expires_in")+"");
			} catch (Exception e) {
				// 获取token失败
				logger.error("获取jsapi_ticket失败 errcode:"+ jsonObject.getInt("errcode")+" errmsg:"+ jsonObject.getString("errmsg"));
			}
		}
		return map;
	}
	/**
	 * 获取jsapi_ticket  由 PICC 获取
	 * 
	 * @return Map<String,String>
	 */
	public static Map<String,String> getJsapi_ticket_fromPICC() {
		String userid = SysDicHelper.getInstance().getValueByDicTypeAndDicId("WEIXINUTIL", "USERID");
		String appid = SysDicHelper.getInstance().getValueByDicTypeAndDicId("WEIXINUTIL", "APPID");
		String requestUrl = SysDicHelper.getInstance().getValueByDicTypeAndDicId("WEIXINUTIL", "URL");
//		String requestUrl = "http://11.207.3.162:7810/wx_interface/SMSPOpeningInterfaceService.aspx";
//		String userid="picc-bj-test";
//		String appid="wx6b1ac82950f50fa5";
		
		Date currDate = new Date();
		SimpleDateFormat smf = new SimpleDateFormat("yyyyMMddHHmmss");
		String flowintime = smf.format(currDate);
		String millis = String.valueOf(System.currentTimeMillis());
		String seqno=flowintime+millis.substring(millis.length()-5);
		Map<String, String> map = new HashMap<String, String>();
		Map<String,Map<String,String> >data = new HashMap<String, Map<String,String>>();
		Map<String ,String > dataHead = new HashMap<String,String>();
		dataHead.put("cmd", "GetJsapiTicket");
		dataHead.put("seqno", seqno);
//		dataHead.put("flowintime", "YYYYMMDDHHMMSS");
		dataHead.put("flowintime", flowintime);
		dataHead.put("userid", userid);
		dataHead.put("token", Md5Util.MD5(userid+flowintime+appid).toUpperCase());
		dataHead.put("request_id", "");
		dataHead.put("server_version", "");
		Map<String ,String > dataBody = new HashMap<String,String>();
		data.put("head", dataHead);
		data.put("body", dataBody);
		JSONObject jsonObject = new JSONObject();
		jsonObject= JSONObject.fromObject(data);
		jsonObject = httpRequestPICC(requestUrl, "POST", jsonObject.toString());
		// 如果请求成功
		if (null != jsonObject) {
			Map<String,String> returnHead = (Map<String,String>)jsonObject.get("head");
			Map<String,String> returnBody = (Map<String,String>)jsonObject.get("body");
			if(null != returnBody && dataHead.get("seqno").equals(returnHead.get("seqno"))){
				try {
					map.put("jsapi_ticket", returnBody.get("jsapi_ticket"));
					logger.error("获取jsapi_ticket:"+ returnBody.get("jsapi_ticket"));
					logger.error("获取expired_dt:"+ returnBody.get("expired_dt"));
				} catch (Exception e) {
				    // 获取token失败
					logger.error("获取jsapi_ticket失败 errcode:"+ jsonObject.getInt("errcode")+" errmsg:"+ jsonObject.getString("errmsg"));
				}
			}
		}
		return map;
	}
	/**
	 * 获取accessToken  由 PICC 获取
	 * 
	 * @return Map<String,String>
	 */
	public static Map<String,String> getaccessToken_fromPICC() {
		String userid = SysDicHelper.getInstance().getValueByDicTypeAndDicId("WEIXINUTIL", "USERID");
		String appid = SysDicHelper.getInstance().getValueByDicTypeAndDicId("WEIXINUTIL", "APPID");
		String requestUrl = SysDicHelper.getInstance().getValueByDicTypeAndDicId("WEIXINUTIL", "URL");
//		String requestUrl = "http://11.207.3.162:7810/wx_interface/SMSPOpeningInterfaceService.aspx";
//		String userid="picc-bj-test";
//		String appid="wx6b1ac82950f50fa5";
		Date currDate = new Date();
		SimpleDateFormat smf = new SimpleDateFormat("yyyyMMddHHmmss");
		String flowintime = smf.format(currDate);
		String millis = String.valueOf(System.currentTimeMillis());
		String seqno=flowintime+millis.substring(millis.length()-5);
		Map<String, String> map = new HashMap<String, String>();
		Map<String,Map<String,String> >data = new HashMap<String, Map<String,String>>();
		Map<String ,String > dataHead = new HashMap<String,String>();
		dataHead.put("cmd", "GetAccessToken");
		dataHead.put("seqno", seqno);
		dataHead.put("flowintime", flowintime);
		dataHead.put("userid", userid);
		dataHead.put("token", Md5Util.MD5(userid+flowintime+appid).toUpperCase());
		dataHead.put("request_id", "");
		dataHead.put("server_version", "");
		Map<String ,String > dataBody = new HashMap<String,String>();
		data.put("head", dataHead);
		data.put("body", dataBody);
		JSONObject jsonObject = new JSONObject();
		jsonObject= JSONObject.fromObject(data);
		jsonObject = httpRequestPICC(requestUrl, "POST", jsonObject.toString());
		// 如果请求成功
		if (null != jsonObject) {
			Map<String,String> returnHead = (Map<String,String>)jsonObject.get("head");
			Map<String,String> returnBody = (Map<String,String>)jsonObject.get("body");
			if(null != returnBody && dataHead.get("seqno").equals(returnHead.get("seqno"))){
				try {
					map.put("access_token", returnBody.get("access_token"));
				} catch (Exception e) {
				    // 获取access_token失败
					logger.error("获取access_token失败 errcode:"+ jsonObject.getInt("errcode")+" errmsg:"+ jsonObject.getString("errmsg"));
				}
			}
		}
		return map;
	}

	/**
	 * 获取用户是否绑定  由 PICC 获取
	 *  获取绑定用户的保单信息
	 *  @param String openid   唯一确定一个用户，是微信号的加密形式
	 * @return Map<String,String> 返回map 【未绑定】为空代表，【已绑定】 有身份证信息代表
	 * @author wangjinhua
	 *  2015-12-11 18:10:44
	 */
	public static Map<String,String> getCustBindInfoBound(String openid) {
		String userid = SysDicHelper.getInstance().getValueByDicTypeAndDicId("WEIXINUTIL", "USERID");
		String appid = SysDicHelper.getInstance().getValueByDicTypeAndDicId("WEIXINUTIL", "APPID");
		String requestUrl = SysDicHelper.getInstance().getValueByDicTypeAndDicId("WEIXINUTIL", "URL");
		Date currDate = new Date();
		SimpleDateFormat smf = new SimpleDateFormat("yyyyMMddHHmmss");
		String flowintime = smf.format(currDate);
		String millis = String.valueOf(System.currentTimeMillis());
		String seqno=flowintime+millis.substring(millis.length()-5);
		Map<String, String> map = new HashMap<String, String>();
		Map<String,Map<String,String> >data = new HashMap<String, Map<String,String>>();
		Map<String ,String > dataHead = new HashMap<String,String>();
		dataHead.put("cmd", "GetCustBindInfo");//获取绑定用户的保单信息
		dataHead.put("seqno", seqno);
		dataHead.put("flowintime", flowintime);
		dataHead.put("userid", userid);
		dataHead.put("token", Md5Util.MD5(userid+flowintime+appid).toUpperCase());
		dataHead.put("request_id", "");
		dataHead.put("server_version", "");
		Map<String ,String > dataBody = new HashMap<String,String>();
		dataBody.put("openid", openid);
		data.put("head", dataHead);
		data.put("body", dataBody);
		JSONObject jsonObject = new JSONObject();
		jsonObject= JSONObject.fromObject(data);
		jsonObject = httpRequestPICC(requestUrl, "POST", jsonObject.toString());
		// 如果请求成功
		if (null != jsonObject) {
			Map<String,Object> returnHead = (Map<String,Object>)jsonObject.get("head");
			map.put("retcode", returnHead.get("retcode").toString());
			if("0".equals(returnHead.get("retcode"))){//成功
				Map<String,Object> returnBody = (Map<String,Object>)jsonObject.get("body");
				if(null != returnBody && dataHead.get("seqno").equals(returnHead.get("seqno"))){
					try {
						map.put("openid", returnBody.get("openid").toString());
						JSONArray jsonArray = (JSONArray) returnBody.get("data");
						if (jsonArray.size()>0) {
							Map<String,String> mapTemp = (Map<String, String>)jsonArray.get(0);
							map.putAll(mapTemp);
						}
						
					} catch (Exception e) {
						// 获取token失败
						logger.error("获取用户绑定信息失败:"+e.getMessage());
					}
				}
			}else{
				logger.error("获取用户绑定信息失败 retcode:"+ returnHead.get("retcode")+" retmsg:"+ returnHead.get("retmsg"));
			}
		}
		return map;
	}
	
	/**
	 * 创建菜单
	 * 
	 * @param menu 菜单实例
	 * @param accessToken 有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(Menu menu, String accessToken) {
		int result = 0;

		// 拼装创建菜单的url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				//log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}

		return result;
	}
	/**
	 * 生成二维码
	 * 
	 * @param menu 菜单实例
	 * @param accessToken 有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createQrCode(JSONObject jsonQrCode, String accessToken) {
		int result = 0;

		// 拼装创建菜单的url
		String url = qrcode_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String qrCode = jsonQrCode.toString();
		// 调用接口创建菜单
		String jsonObject = HttpRequestUtil.sendHttpPostRequest(url, qrCode);


		return result;
	}
	public static String getOnlyId(){
		String beginDate="1328007600000";  
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		String sd = sdf.format(new Date(Long.parseLong(beginDate)));  
		System.out.println(sd);  
		return sd;
	}
	
	/**
     * 
    		* 描述:下载指定http地址的文件
    		* @param URI
    		* @param proxy 是否使用代理访问外网链接
    		* @return 文件的base64码
    		* @throws Exception
    		* @author 朱久满 2015年12月16日 上午9:15:40
     */
    public static String getFileFromURI(String URI, boolean proxy) throws Exception{
    	try {
    		boolean proxySet = Boolean.valueOf(SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxySet"));
    		String proxyHost = SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxyHost");
    		String proxyPort = SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxyPort");
    		if(proxy){
    			System.getProperties().put("proxySet", proxySet);
    			System.getProperties().put("proxyHost", proxyHost);
    			System.getProperties().put("proxyPort", proxyPort);
    		}
			URL url = new URL(URI);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			String str  = WeixinUtil.getFileEndWitsh(conn.getHeaderField("Content-Type"));
			String str  = "jpg";
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.getInputStream();
			BufferedImage image =ImageIO.read(conn.getInputStream());
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			OutputStream b64 = new Base64OutputStream(os);
			ImageIO.write(image, str, b64);
			return os.toString("UTF-8");
		} catch (Exception e) {
			throw e;
		} finally {
			System.getProperties().remove("proxySet");
			System.getProperties().remove("proxyHost");
			System.getProperties().remove("proxyPort");
		}
    }
    
    public static String downloadImage(String media_id){
    	try {
    		Map<String,String> map = getaccessToken_fromPICC();
    		String access_token = map.get("access_token");
    		String url = file_get_url.replace("ACCESS_TOKEN", access_token).replace("MEDIA_ID", media_id);
    		return getFileFromURI(url,true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("地址:"+file_get_url+"下载图片失败:"+e.getMessage());
		}
    	return null;
    }
    
    
    /**
     * 
	 * 描述:获取人工服务
	 * @param openid
	 * @return
	 * @author 辛武涛 2017年10月26日 下午3:34:44
     */
	public static String getClientService(String openid) {
		String userid = "picc-bj";//SysDicHelper.getInstance().getValueByDicTypeAndDicId("WEIXINUTIL", "USERID");
		String appid = "wx6b1ac82950f50fa5";//SysDicHelper.getInstance().getValueByDicTypeAndDicId("WEIXINUTIL", "APPID");
		String requestUrl = "http://10.9.16.9:7810/wx_interface/SMSPOpeningInterfaceService.aspx";//SysDicHelper.getInstance().getValueByDicTypeAndDicId("WEIXINUTIL", "URL");
		Date currDate = new Date();
		SimpleDateFormat smf = new SimpleDateFormat("yyyyMMddHHmmss");
		String flowintime = smf.format(currDate);
		String millis = String.valueOf(System.currentTimeMillis());
		String seqno=flowintime+millis.substring(millis.length()-5);
		Map<String, String> map = new HashMap<String, String>();
		Map<String,Map<String,String> >data = new HashMap<String, Map<String,String>>();
		Map<String ,String > dataHead = new HashMap<String,String>();
		dataHead.put("cmd", "GetTalkSession");//获取人工客服信息
		dataHead.put("seqno", seqno);
		dataHead.put("flowintime", flowintime);
		dataHead.put("userid", userid);
		dataHead.put("token", Md5Util.MD5(userid+flowintime+appid).toUpperCase());
		dataHead.put("request_id", "");
		dataHead.put("server_version", "");
		Map<String ,String > dataBody = new HashMap<String,String>();
		dataBody.put("openid", openid);
		dataBody.put("content", "欢迎您选择\"北京人保财险\"人工客服，转接中~~");
		data.put("head", dataHead);
		data.put("body", dataBody);
		JSONObject jsonObject = new JSONObject();
		jsonObject= JSONObject.fromObject(data);
		jsonObject = httpRequestPICC(requestUrl, "POST", jsonObject.toString());
		// 如果请求成功
		if (null != jsonObject) {
			Map<String,Object> returnHead = (Map<String,Object>)jsonObject.get("head");
			map.put("retcode", returnHead.get("retcode").toString());
			if("0".equals(returnHead.get("retcode"))){//成功
				return "0";
			}else{
				logger.error("获取用户绑定信息失败 retcode:"+ returnHead.get("retcode")+" retmsg:"+ returnHead.get("retmsg"));
				return "1";
			}
		} else {
			return "1";
		}
	}
    
    
	public static void main(String[] args) {
		try {
			
			System.out.println(WeixinUtil_test.getClientService("oXHv4jvBlFKQl8D4C2Nxau7bz0LA"));
			
			//发送模板消息
//		String access_token = getAccessToken("wxe6419d8ba2e88c00", "d4624c36b6795d1d99dcf0547af5443d").getToken();
//		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
//		Map<String ,Object > dataParam = new HashMap<String,Object>();
//		Map<String ,String > test = new HashMap<String,String>();
//		test.put("value", "O(∩_∩)O哈哈~");
//		test.put("color", "#173177");
//		dataParam.put("test", test);
//		Map<String ,Object > data = new HashMap<String,Object>();
//		data.put("touser", "oaZaHwAd5rHKLSKYedlR3eweOPwE");//openid-朱久满
//		data.put("template_id", "Guyb94dHCd3IAGaj29XCO7OR5VTcF0hmATHJJcI2dOQ");
//		data.put("topcolor", "#FF0000");
//		data.put("data", dataParam);
////		data.put("url", "");
//		JSONObject jsonObject = new JSONObject();
//		jsonObject= JSONObject.fromObject(data);
//		System.out.println(jsonObject);
//		jsonObject = httpRequestPICC(requestUrl, "POST", jsonObject.toString());
//		// 如果请求成功
//		System.out.println(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		getaccessToken_fromPICC();
	}
}
