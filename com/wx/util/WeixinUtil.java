package com.wx.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.sys.dic.SysDicHelper;
import com.sys.dic.entity.DicContentEntity;
import com.sys.exception.EpiccException;
import com.wap.kfcenter.entity.carslist.CarsListReturnEntity;
import com.wap.util.ConstantUtils;
import com.wap.util.ServiceFactoryBean;
import com.wx.model.pojo.AccessToken;
import com.wx.model.pojo.Menu;


/**
 * 公众平台通用接口工具类
 * 
 * @author 胡忠淦
 * @date 2013-12-09
 */
public class WeixinUtil {
    //redis
	private static JedisPool jedisPool = null;
	
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	//private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);  
	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	//二维码创建post
	public static String qrcode_create_url="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
	
	//获取jsapi_ticket
	public static String jsapi_ticket_url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	
	//下载媒体文件地址get
//	public static String file_get_url="https://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	public static String file_get_url="https://file.api.weixin.qq.com/cgi-bin/media/get";
			
	//获得模板ID
	public static String get_template_id_url="https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";
	
	//发送模板消息
	public static String template_message_send_url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	//发送客服消息
	public static String custom_message_send_url="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	
	//获取微信用户信息
	public static String wx_user_info_url="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	
	//获取企业微信access_token
	public static String qywx_access_token_url="https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ID&corpsecret=SECRECT";
	
	//获取企业微信用户信息
	public static String qywx_getuserinfo_url="https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
	                                           
	private static final Log logger = LogFactory.getLog(WeixinUtil.class);
	private static String userid;
	private static String appid;
	private static String requestUrl;
	private static String appsecret;
	private static int TIME_OUT = 60000;
	
	//企业微信要求参数
	private static String corpid;
	private static String agentid1;
	private static String corpsecret;
	static{
		userid = SysDicHelper.getInstance().getValueByDicTypeAndDicId("WEIXINUTIL", "USERID");
		appid = SysDicHelper.getInstance().getValueByDicTypeAndDicId("WEIXINUTIL", "APPID");
//		appsecret= ConfigUtil.getString("appSecret");
		appsecret = SysDicHelper.getInstance().getValueByDicTypeAndDicId("WEIXINUTIL", "APPSECRET");
		requestUrl = SysDicHelper.getInstance().getValueByDicTypeAndDicId("WEIXINUTIL", "URL");
		
		corpid = SysDicHelper.getInstance().getValueByDicTypeAndDicId("QYWXUTIL", "CORPID");
		agentid1 = SysDicHelper.getInstance().getValueByDicTypeAndDicId("QYWXUTIL", "AGENTID1");
		corpsecret = SysDicHelper.getInstance().getValueByDicTypeAndDicId("QYWXUTIL", "CORPSECRET");
//		requestUrl = "http://10.133.12.20:7810/wx_interface/SMSPOpeningInterfaceService.aspx";//正式环境接口地址
//		userid="picc-bj";//正式环境userid
//		appid="wx953a63ae6f5582f1";//正式环境公众号appid
		//测试环境
		requestUrl = "http://10.9.16.9:7810/wx_interface/SMSPOpeningInterfaceService.aspx";
		userid="picc-bj";
		appid="wx6b1ac82950f50fa5";
	}
	
	/**
	 * 描述:获取JedisPool，后面调用的地方统一使用getJedisPool方法获取JedisPool
	 * @return JedisPool
	 * @author 吕亮 2018年4月25日 下午2:45:54
	 */
	public static JedisPool getJedisPool() {
		if(jedisPool == null){
			jedisPool = (JedisPool)ServiceFactoryBean.getService("jedisPool");
		}
		return jedisPool;
	}
	
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		logger.info("请求报文:"+outputStr);
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(null,requestUrl,new sun.net.www.protocol.https.Handler());
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
			logger.info("响应报文:"+jsonObject);
		} catch (ConnectException ce) {
			System.out.println("Weixin server connection timed out.");
			System.out.println(ce);
			logger.error("请求异常:"+ce.getMessage());
		} catch (Exception e) {
			System.out.println("https request error");
			System.out.println(e);
			logger.error("请求异常:"+e.getMessage());
		}
		return jsonObject;
	}
	public static JSONObject httpRequestPICC(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		logger.info("请求报文:"+outputStr);
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection)url.openConnection();
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setRequestProperty("Content-Type","text/json");
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
			ce.printStackTrace();
			logger.error("请求失败:"+ce.getMessage());
		} catch (Exception e) {
			System.out.println("https request error");
			e.printStackTrace();
			logger.error("请求失败:"+e.getMessage());
		}
		logger.info("响应报文:"+jsonObject);
		return jsonObject;
	}
	/**
	 * 
			* 描述:获取模板id
			* @param shortid
			* @return
			* @author 朱久满 2016年1月27日 下午1:43:22
	 */
	public static String getTemplateId(String shortid){
		String access_token = getaccessToken_fromPICC().get("access_token");
		Map<String, String> map = new HashMap<String, String>();
		map.put("template_id_short", shortid);
		JSONObject json = JSONObject.fromObject(map);
		String url = get_template_id_url.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonObject = httpRequest(url, "POST", json.toString());
		return jsonObject.get("template_id").toString();
	}
	/**
	 * 
	 * 描述:发送模板消息
	 * @param data
	 * @return
	 * @author 朱久满 2016年1月27日 下午1:43:22
	 */
	public static JSONObject sendTemplateMessage(String jsondata){
		String access_token = getaccessToken_fromPICC().get("access_token");
		String url = template_message_send_url.replace("ACCESS_TOKEN", access_token);
		boolean proxySet = Boolean.valueOf(SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxySet"));
		String proxyHost = SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxyHost");
		String proxyPort = SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxyPort");
		System.getProperties().put("proxySet", proxySet);//true:使用代理 false:不使用
		System.getProperties().put("proxyHost", proxyHost);//代理服务器IP
		System.getProperties().put("proxyPort", proxyPort);//代理服务器端口
		JSONObject jsonObject = httpRequest(url, "POST", jsondata);
		System.getProperties().remove("proxySet");
		System.getProperties().remove("proxyHost");
		System.getProperties().remove("proxyPort");
		return jsonObject;
	}
	/**
	 * 
	 * 描述:发送客服消息
	 * @param data
	 * @return
	 * @author 朱久满 2016年1月27日 下午1:43:22
	 */
	public static JSONObject sendCustomMessage(String jsondata){
		String access_token = getaccessToken_fromPICC().get("access_token");
		String url = custom_message_send_url.replace("ACCESS_TOKEN", access_token);
		boolean proxySet = Boolean.valueOf(SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxySet"));
		String proxyHost = SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxyHost");
		String proxyPort = SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxyPort");
		System.getProperties().put("proxySet", proxySet);//true:使用代理 false:不使用
		System.getProperties().put("proxyHost", proxyHost);//代理服务器IP
		System.getProperties().put("proxyPort", proxyPort);//代理服务器端口
		JSONObject jsonObject = httpRequest(url, "POST", jsondata);
		System.getProperties().remove("proxySet");
		System.getProperties().remove("proxyHost");
		System.getProperties().remove("proxyPort");
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
		Map<String, String> map = new HashMap<String, String>();
		Map<String,Map<String,String> >data = new HashMap<String, Map<String,String>>();
		Map<String ,String > dataHead = getRequestHeadPICC("GetJsapiTicket");
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
		Map<String, String> map = new HashMap<String, String>();
		Map<String,Map<String,String> >data = new HashMap<String, Map<String,String>>();
		Map<String ,String > dataHead = getRequestHeadPICC("GetAccessToken");
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
	public static List<Map<String,String>> getCustBindInfoBound(String openid) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String,Map<String,String> >data = new HashMap<String, Map<String,String>>();
		Map<String ,String > dataHead = getRequestHeadPICC("GetCustBindInfo");
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
						logger.error("获取用户绑定信息:"+jsonArray);
						return jsonArray;
					} catch (Exception e) {
						// 获取token失败
						logger.error("获取用户绑定信息失败:"+e.getMessage());
					}
				}
			}else{
				logger.error("获取用户绑定信息失败 retcode:"+ returnHead.get("retcode")+" retmsg:"+ returnHead.get("retmsg"));
			}
		}
		return null;
	}
	/**
	 * 
			* 描述:绑定用户信息
			* @param map
			* @return
			* @author 朱久满 2016年1月28日 上午9:42:49
	 */
	public static Map<String,String> bound(Map<String,String> map) {
		Map<String, String> returnmap = new HashMap<String, String>();
		Map<String,Map<String,String> >data = new HashMap<String, Map<String,String>>();
		Map<String ,String > dataHead = getRequestHeadPICC("SendCustBindInfo");
		data.put("head", dataHead);
		data.put("body", map);
		JSONObject jsonObject = new JSONObject();
		jsonObject= JSONObject.fromObject(data);
		jsonObject = httpRequestPICC(requestUrl, "POST", jsonObject.toString());
		// 如果请求成功
		if (null != jsonObject) {
			Map<String,String> returnHead = (Map<String,String>)jsonObject.get("head");
			Map<String,String> returnBody = (Map<String,String>)jsonObject.get("body");
			if(null != returnBody && dataHead.get("seqno").equals(returnHead.get("seqno"))){
				try {
					returnmap.put("res", returnHead.get("retcode"));
					if(!"0".equals(returnHead.get("retcode"))){
						returnmap.put("msg", returnHead.get("retmsg"));
						logger.error("用户openid:"+map.get("openid")+"绑定失败 retcode:"+ returnHead.get("retcode")+" retmsg:"+ returnHead.get("retmsg"));
					}
				} catch (Exception e) {
				    // 获取access_token失败
					logger.error("获取access_token失败 errcode:"+ jsonObject.getInt("errcode")+" errmsg:"+ jsonObject.getString("errmsg"));
				}
			}
		}
		return returnmap;
	}
	
	/**
	 * 获取用户用户信息，username、identifyno、identifytype、openid
	 * 规则：接口方法GetUserName
	 * 接口要求：必须在微信端进行登录，方可获取到用户信息
	 * @param String openid   唯一确定一个用户，是微信号的加密形式
	 * @return Map<String,String> 返回map retcode=0成功并带有身份证号，retcode=1失败并带有失败信息
	 * @author lvliang
	 * @date 2016-07-18 11:10:44
	 */
	public static Map<String,String> getUserNameFormPICC(String openid) {
		//升级正式注释掉下面url，下面的url是微信正式环境接口地址，由于测试环境注册信息有问题，所以用正式环境进行验证
//		String url = "http://10.133.12.20:7810/wx_interface/SMSPOpeningInterfaceService.aspx";
		Map<String, String> returnmap = new HashMap<String, String>();
		Map<String,Map<String,String> >data = new HashMap<String, Map<String,String>>();
		Map<String ,String > dataHead = getRequestHeadPICC("GetUserName");
		//测试用
		//Map<String ,String > dataHead = getRequestHeadPICCTest("GetUserName");
		Map<String ,String > dataBody = new HashMap<String,String>();
		dataBody.put("openid", openid);
		data.put("head", dataHead);
		data.put("body", dataBody);
		JSONObject jsonObject = null;
		//JSONObject jsonObject = new JSONObject();
		//jsonObject= JSONObject.fromObject(data);
		//jsonObject = httpRequestPICC(requestUrl, "POST", jsonObject.toString());
		//测试用
		//jsonObject = httpRequestPICC(url, "POST", jsonObject.toString());
		String retcode = "";
		String retmsg = "";
		String seqno = "";
		if (null != jsonObject) {
			Map<String,Object> returnHead = (Map<String,Object>)jsonObject.get("head");
			Map<String,Object> returnBody = (Map<String,Object>)jsonObject.get("body");
			try {
				if(returnHead != null){
					retcode = returnHead.get("retcode").toString();
					retmsg = returnHead.get("retmsg").toString();
					seqno = returnHead.get("seqno").toString();
					returnmap.put("retcode", retcode);
					returnmap.put("retmsg", retmsg);
					if(dataHead.get("seqno").equals(seqno)){
						if("0".equals(retcode)){
							if(returnBody != null){
								returnmap.put("openid", returnBody.get("openid").toString());
								returnmap.put("username", returnBody.get("username").toString());
								returnmap.put("identifyno", returnBody.get("identifyno").toString());
								returnmap.put("identifytype", returnBody.get("identifytype").toString());
								logger.info("GetUserName response openid===" + returnBody.get("openid"));
								logger.info("GetUserName response username===" + returnBody.get("username"));
								logger.info("GetUserName response identifyno===" + returnBody.get("identifyno"));
								logger.info("GetUserName response identifytype===" + returnBody.get("identifytype"));
							}else{
								returnmap.put("retcode", "1");
								returnmap.put("retmsg", "接口返回报文体信息为空！");
							}
						}
					}else{
						returnmap.put("retcode", "1");
						returnmap.put("retmsg", "接口返回报文信息与请求信息不是一对！");
					}
				}else{
					returnmap.put("retcode", "1");
					returnmap.put("retmsg", "接口返回报文头信息错误！");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		测试用账号登陆：
		if("osX-mwHc1xa7SZEjYG4fIPyFUSCo".equals(openid) || "osX-mwAveQlCkBjHCprRy0lUdP9M".equals(openid) || 
				"osX-mwGsVPC5wTYU1eYB7Dxcvmrg".equals(openid) || "osX-mwB4Dj4l0Fs80O5ew92mVRsE".equals(openid) || 
				"osX-mwBbDPmbJ5Kvkr4xBP42__tM".equals(openid) || "osX-mwG6e3DHFFuY0Ni0ibaeLcKM".equals(openid) ||
				"osX-mwJx7-OFry76OIAxVzj85wvk".equals(openid) || "osX-mwCJ6N1LTt4OGyifihi0e4cg".equals(openid) ||
				"osX-mwN8dRqA1Z7SqzPvqysgxiVQ".equals(openid) || "osX-mwP4DP-xlIDzZU4vI-tK3md8".equals(openid) ||
				"osX-mwKcjCg0ntmhcFyOByQu1bZc".equals(openid) ){
			logger.info("测试用户登陆");
			returnmap.put("retcode", "0");
			returnmap.put("openid", openid);
			returnmap.put("username", openid.substring(openid.length()-10));//
			returnmap.put("identifyno", "111111");
			returnmap.put("identifytype", "01");
		}	
		return returnmap;
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
    	ByteArrayOutputStream os = null;
    	OutputStream b64 = null;
    	try {
    		CloseableHttpClient client = null;
 		   	try { 
 	            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(  
 	                    null, new TrustStrategy() {  
 	                        public boolean isTrusted(X509Certificate[] chain,  
 	                                String authType) throws CertificateException {  
 	                            return true;  
 	                        }  
 	                    }).build();  
 	            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(  
 	                    sslContext, new X509HostnameVerifier() {  
 	  
 	                        public boolean verify(String arg0, SSLSession arg1) {  
 	                            return true;  
 	                        }  
 	  
 	                        public void verify(String host, SSLSocket ssl)  
 	                                throws IOException {  
 	                        }  
 	  
 	                        public void verify(String host, X509Certificate cert)  
 	                                throws SSLException {  
 	                        }  
 	  
 	                        public void verify(String host, String[] cns,  
 	                                String[] subjectAlts) throws SSLException {  
 	                        }  
 	  
 	                    });  
 	            	client =  HttpClients.custom().setSSLSocketFactory(sslsf).build();  
 		   	} catch (GeneralSecurityException e) {  
 	            throw e;  
 		   	}  
 		   	HttpGet httpGet = new HttpGet(URI);
 		   	if(proxy){
 		   		Builder customReqConfBuilder = RequestConfig.custom();
 		   		String proxyHost = SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxyHost");
 		   		String proxyPort = SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxyPort");
 		   		HttpHost httpProxy = new HttpHost(proxyHost, Integer.valueOf(proxyPort));
 		   		customReqConfBuilder.setProxy(httpProxy);
 		   		RequestConfig customReqConf = customReqConfBuilder.build();
 		   		httpGet.setConfig(customReqConf );
 		   	}
 		   	//测试时使用 代理写死 
// 		   	Builder customReqConfBuilder = RequestConfig.custom();
//	   		HttpHost httpProxy = new HttpHost("12.1.37.247", 8080);
//	   		customReqConfBuilder.setProxy(httpProxy);
//	   		RequestConfig customReqConf = customReqConfBuilder.build();
//	   		httpGet.setConfig(customReqConf);
 			HttpResponse res = client.execute(httpGet);
			// 将返回的输入流转换成字符串
 			int statusCode = res.getStatusLine().getStatusCode();
			if (statusCode==200) {
 				HttpEntity resEntity = res.getEntity();
 				String contentType = resEntity.getContentType().getValue();
 				if("image/jpeg".equals(contentType)){
 					BufferedImage image =ImageIO.read(resEntity.getContent());
 					os = new ByteArrayOutputStream();
 					b64 = new Base64OutputStream(os);
 					String str  = resEntity.getContentType().getValue().split("/")[1];//获取文件扩展名
 					ImageIO.write(image, str, b64);
 					return os.toString("UTF-8");
 				}else{
 					logger.info("[请求微信资源url:"+URI+"]-[响应信息:"+EntityUtils.toString(resEntity, "UTF-8")+"]");
 					throw new EpiccException("微信接口响应异常");
 				}
 			}else{
 				logger.info("[请求微信资源url:"+URI+"]-[响应状态码:"+statusCode+"]");
 				throw new EpiccException("微信接口响应异常");
 			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(os!=null){
				os.close();
			}
			if(b64!=null){
				b64.close();
			}
		}
    }
    
    public static String downloadImage(String media_id) throws Exception{
    	String url = "";
    	try {
    		Map<String,String> map = getaccessToken_fromPICC();
    		String access_token = map.get("access_token");
    		System.out.println("access_token=" + access_token);
    		System.out.println("file_get_url=" + file_get_url);
    		url = file_get_url+"?access_token="+access_token+"&media_id="+media_id;
    		return getFileFromURI(url,true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("地址:"+url+"下载图片失败:"+e.getMessage());
			throw e;
		}
    }
    
    private static Map<String,String> getRequestHeadPICC(String method){
    	Date currDate = new Date();
		SimpleDateFormat smf = new SimpleDateFormat("yyyyMMddHHmmss");
		String flowintime = smf.format(currDate);
		String millis = String.valueOf(System.currentTimeMillis());
		String seqno=flowintime+millis.substring(millis.length()-5);
		Map<String ,String > dataHead = new HashMap<String,String>();
		dataHead.put("cmd", method);
		dataHead.put("seqno", seqno);
		dataHead.put("flowintime", flowintime);
		dataHead.put("userid", userid);
		dataHead.put("token", Md5Util.MD5(userid+flowintime+appid).toUpperCase());
		dataHead.put("request_id", "");
		dataHead.put("server_version", "V1.1");
		return dataHead;
    }
    
    private static Map<String,String> getRequestHeadPICCTest(String method){
    	Date currDate = new Date();
		SimpleDateFormat smf = new SimpleDateFormat("yyyyMMddHHmmss");
		String flowintime = smf.format(currDate);
		String millis = String.valueOf(System.currentTimeMillis());
		String seqno=flowintime+millis.substring(millis.length()-5);
		Map<String ,String > dataHead = new HashMap<String,String>();
		dataHead.put("cmd", method);
		dataHead.put("seqno", seqno);
		dataHead.put("flowintime", flowintime);
		dataHead.put("userid", "picc-bj");
		dataHead.put("token", Md5Util.MD5("picc-bj"+flowintime+"wx953a63ae6f5582f1").toUpperCase());
		dataHead.put("request_id", "");
		dataHead.put("server_version", "V1.1");
		return dataHead;
    }
    
	public static void main(String[] args) throws Exception {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("openid", "osX-mwJTlw0L2qr0I1dU4Jfw7L7g");
//		map.put("openid", "osX-mwBY7n4b-Uzr1v3g_Ucl6vdg");
//		map.put("first", "尊敬的人保客户您好");
//		map.put("keyword1", "RDZA201511026733000581");
//		map.put("keyword2", "京QH5J68");
//		map.put("remark", "您的索赔材料审核未通过，请重新提交！不通过原因如下：身份证照片不清晰，银行卡照片卡号不清晰。");
//		map.put("openid", "oXHv4jvBlFKQl8D4C2Nxau7bz0LA");
		//map.put("content", "测试客服消息,尊敬的客户您好,您上传的索赔材料未能通过审核,请重新上传!");
//		JSONObject jsonObject = new JSONObject();
//		jsonObject= JSONObject.fromObject(map);
//		
//		jsonObject = httpRequestPICC("http://12.1.80.131:5709/bjwxplat/wx/interface?target=sendCustomMessage", "POST", jsonObject.toString());
//		jsonObject = httpRequestPICC("http://127.0.0.1:7001/bjwxplat/wx/interface?target=sendAuditRes", "POST", jsonObject.toString());
//		jsonObject = httpRequestPICC("http://12.1.80.148:5712/bjwxplat/wx/interface?target=sendAuditRes", "POST", jsonObject.toString());
//		System.out.println(WeixinUtil.getCustBindInfoBound("oXHv4jvBlFKQl8D4C2Nxau7bz0LA"));;
//		System.out.println(WeixinUtil.getCustBindInfoBound("osX-mwJTlw0L2qr0I1dU4Jfw7L7g"));;
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("openid", "osX-mwJTlw0L2qr0I1dU4Jfw7L7g");
//		map.put("comcode", "11000000");
//		map.put("cardno", "370725198009184180");
//		map.put("plateno", "京QH5J68");
//		map.put("identifytype", "01");
//		System.out.println(WeixinUtil.bound(map));
		
//		String requestUrl = "http://11.207.3.162:7810/wx_interface/SMSPOpeningInterfaceService.aspx";
//		String userid="picc-bj-test";
//		String appid="wx6b1ac82950f50fa5";
//		Date currDate = new Date();
//		SimpleDateFormat smf = new SimpleDateFormat("yyyyMMddHHmmss");
//		String flowintime = smf.format(currDate);
//		String millis = String.valueOf(System.currentTimeMillis());
//		String seqno=flowintime+millis.substring(millis.length()-5);
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("openid", "osX-mwJTlw0L2qr0I1dU4Jfw7L7g");
//		map.put("insuredidentinumber", "32032219800904062X");
//		map.put("licenseno", "京PVU258");
//		Map<String,Object >data2 = new HashMap<String,Object>();
//		Map<String ,String > dataHead = new HashMap<String,String>();
//		dataHead.put("cmd", "DeleteUserBind");//获取绑定用户的保单信息
//		dataHead.put("cmd", "GetCustBindInfo");//获取绑定用户的保单信息
//		dataHead.put("seqno", seqno);
//		dataHead.put("flowintime", flowintime);
//		dataHead.put("userid", userid);
//		dataHead.put("token", Md5Util.MD5(userid+flowintime+appid).toUpperCase());
//		dataHead.put("request_id", "");
//		dataHead.put("server_version", "");
//		data2.put("head", dataHead);
//		data2.put("body", map);
//		JSONObject jsonObject = new JSONObject();
//		jsonObject= JSONObject.fromObject(data2);
//		System.out.println(jsonObject);
//		jsonObject = httpRequestPICC(requestUrl, "POST", jsonObject.toString());
//		System.out.println(jsonObject);
//		try {
			//发送客服消息测试
//			System.out.println(getaccessToken_fromPICC());
		
//		Map<String,String> map = getaccessToken_fromPICC();
//		System.out.println(map.get("access_token")+"***********");
//		String tmp = downloadImage("xRT9POx329lxiZoAypETPSav0KkqLi93_ewhymJOgqLJRdvrmmVW8anA-Yb_-byf");
//		System.out.println(tmp);
//		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=js6W_E31VxpQGZFwJgog6U8Wqso2QeYWIirOOcDPySJkN1NflXe1AsX7X8YxP2BzilyRxQlYP9-lO-bTcp21d1AxG6tgJIvK5Phf9IoPyn0WURhAJAAYV";
//		Map<String ,String > dataText = new HashMap<String,String>();
//		dataText.put("content", "测试消息");
//		Map<String ,Object > data = new HashMap<String,Object>();
//		data.put("touser", "osX-mwBY7n4b-Uzr1v3g_Ucl6vdg");//openid-邓金鑫
////		data.put("touser", "osX-mwJTlw0L2qr0I1dU4Jfw7L7g");//openid-朱久满
//		data.put("msgtype", "text");
//		data.put("text", dataText);
//		JSONObject jsonObject = new JSONObject();
//		jsonObject= JSONObject.fromObject(data);
//		System.out.println(jsonObject);
//		jsonObject = httpRequestPICC(requestUrl, "POST", jsonObject.toString());
//		// 如果请求成功
//		System.out.println(jsonObject);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		getaccessToken_fromPICC();
//		try {
//			System.out.println(getFileFromURI("http://127.0.0.1:8080/epicc/common/img/login_img.jpg",false));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
//		System.out.println(UUID.randomUUID().toString().toUpperCase().replaceAll("-", ""));
//		System.out.println(SignUtil.generateSign(UUID.randomUUID().toString().toUpperCase().replaceAll("-", "")));
//		System.getProperties().put("proxySet", true);//true:使用代理 false:不使用
//		System.getProperties().put("proxyHost", "12.1.80.247");//代理服务器IP
//		System.getProperties().put("proxyPort", "8080");//代理服务器端口
//		
//		String requestUrl = qywx_access_token_url.replace("ID", "ww0ee30115eeb5c08a").replace("SECRECT", "4zIyzTviDjVqmP09Zn1BjQNxG_MHFRJOBMkLWXlGgD4");
//		System.out.println("requestUrl=" + requestUrl);
//		String resData = httpsRequest(requestUrl, "GET", null);
//		//JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
//		System.out.println(resData);
//
//		
//		System.getProperties().remove("proxySet");
//		System.getProperties().remove("proxyHost");
//		System.getProperties().remove("proxyPort");
		
	}
	
	/**
	 * 
			* 描述:
			* https请求
			* @param requestUrl
			* @param requestMethod
			* 					GET|POST
			* @param outputStr
			* @return
			* @author 许宝众 2017年6月15日 下午5:05:51
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		logger.info("请求地址："+requestUrl+",请求报文:"+outputStr);
		StringBuffer buffer = new StringBuffer();
		String data = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(null,requestUrl,new sun.net.www.protocol.https.Handler());
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
			if (StringUtils.isNotBlank(outputStr)) {
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
			data = buffer.toString();
			logger.info("响应报文:"+data);
		} catch (ConnectException ce) {
			System.out.println("Weixin server connection timed out.");
			System.out.println(ce);
			logger.error("请求异常:"+ce.getMessage());
		} catch (Exception e) {
			System.out.println("https request error");
			System.out.println(e);
			logger.error("请求异常:"+e.getMessage());
		}
		return data;
	}
		/**
	 * 
	 * 描述:
	 * 	根据微信返回的code获取用户的openid
	 * @param code
	 * @return
	 * @author 许宝众2017年6月15日 下午12:31:26
	 */
	public static Map<String,String> getWxUserOpenId(String code) {
		boolean proxySet = Boolean.valueOf(SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxySet"));
		String proxyHost = SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxyHost");
		String proxyPort = SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxyPort");
		System.getProperties().put("proxySet", proxySet);//true:使用代理 false:不使用
		System.getProperties().put("proxyHost", proxyHost);//代理服务器IP
		System.getProperties().put("proxyPort", proxyPort);//代理服务器端口
		
		Map<String,String> resMap=new HashMap<String,String>();
		String weixinUrl="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		weixinUrl = weixinUrl.replace("APPID", appid);
		weixinUrl = weixinUrl.replace("SECRET", appsecret);
		weixinUrl = weixinUrl.replace("CODE", code);
		String resData = httpsRequest(weixinUrl, "GET", null);
		com.alibaba.fastjson.JSONObject jsonObject=com.alibaba.fastjson.JSONObject.parseObject(resData);
		String errcode=jsonObject.getString("errcode");
		String errmsg=jsonObject.getString("errmsg");
		if(StringUtils.isNotBlank(errcode)){
			resMap.put("errcode", errcode);
			resMap.put("errmsg", errmsg);
		}else{//响应正常
			String openid = (String) jsonObject.get("openid");
			resMap.put("openid", openid);
		}
		
		System.getProperties().remove("proxySet");
		System.getProperties().remove("proxyHost");
		System.getProperties().remove("proxyPort");
		return resMap;
	}
	
	/**
	 * 
			* 描述:
			* 	获取微信授权获取openid地址
			* @param redirectUrl
			* 		微信回调我们的Url
			* @return
			* @author xubaozhong2017年6月16日 下午1:44:22
	 */
	public static String getWxAuthRequestUrl(String redirectUrl){
		//拼接微信授权跳转链接
		String wxPreffix="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri=";
		String wxSuffix="&response_type=code&scope=snsapi_base#wechat_redirect";
		try {
			String encryptUrl = URLEncoder.encode(redirectUrl,"utf-8");
			String url = wxPreffix+encryptUrl+wxSuffix;
			return url;
		} catch (Exception e) {
			throw new RuntimeException("授权链接转换失败");
		}
	}
	/**
	 * 
			* 描述:
			* 根据openId获取微信用户信息
			* @param openId
			* 	
			* @return
			* @author 许宝众 2017年9月30日 上午10:23:59
	 */
	public static com.alibaba.fastjson.JSONObject getWxUserInfo(String openId){
		boolean proxySet = Boolean.valueOf(SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxySet"));
		String proxyHost = SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxyHost");
		String proxyPort = SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxyPort");
		String access_token = getaccessToken_fromPICC().get("access_token");
		Assert.isTrue(StringUtils.isNotBlank(access_token), "access_token must not empty");
		System.getProperties().put("proxySet", proxySet);//true:使用代理 false:不使用
		System.getProperties().put("proxyHost", proxyHost);//代理服务器IP
		System.getProperties().put("proxyPort", proxyPort);//代理服务器端口
		String url = null;
		
		url = wx_user_info_url.replace("ACCESS_TOKEN", access_token ).replace("OPENID", openId);
		logger.info("requestUrl:"+url);
		String resData = httpsRequest(url, "GET", null);
		Assert.isTrue(StringUtils.isNotBlank(resData), "wexin interface exception");
		
		com.alibaba.fastjson.JSONObject jsonObject=com.alibaba.fastjson.JSONObject.parseObject(resData);
		
		System.getProperties().remove("proxySet");
		System.getProperties().remove("proxyHost");
		System.getProperties().remove("proxyPort");
		return jsonObject;
	}
	/**
	 * 
			* 描述:
			* 判断用户是否关注了公众号
			* @param openId
			* @return
			* @author xbz 2017年9月30日 上午10:27:03
	 */
	public static boolean isNotifyPublicAccount(String openId){
		return "1".equals(getWxUserInfo(openId).getString("subscribe"));
	}
	
	/**
	 * 
			* 描述:获取用户的昵称
			* @param openId
			* @return
			* @author 赵硕  2018年4月16日 下午3:18:21
	 */
	public static String getWxUserName(String openId){
	     return getWxUserInfo(openId).getString("nickname");
	}
	
	/**
	 * 
	 * 描述:获取用户车辆列表,接口方法GetCarsList
	 * @param username 用户名称
	 * @return CarsListReturnEntity result,result 为GetCarsList接口返回报文的对象，result.getHead().getRetcod()为0时，成功
	 * @author 戴元守 2017年9月21日 上午9:07:42
	 */
	public static CarsListReturnEntity getCarsListFromPICC(String username) {
		
		//升级正式注释掉下面url，下面的url是微信正式环境接口地址，由于测试环境注册信息有问题，所以用正式环境进行验证
		String url = "http://10.133.12.20:7810/wx_interface/SMSPOpeningInterfaceService.aspx"; //-----------注释
		Map<String,Map<String,String> >data = new HashMap<String, Map<String,String>>();
		Map<String ,String > dataHead = getRequestHeadPICC("GetCarsList");
		//测试用
		//Map<String ,String > dataHead = getRequestHeadPICCTest("GetCarsList");                 
		Map<String ,String > dataBody = new HashMap<String,String>();
		dataBody.put("username", username);
		data.put("head", dataHead);
		data.put("body", dataBody);
		JSONObject jsonObject = new JSONObject();
		jsonObject= JSONObject.fromObject(data);
		jsonObject = httpRequestPICC(requestUrl, "POST", jsonObject.toString());
		//测试用
		//jsonObject = httpRequestPICC(url, "POST", jsonObject.toString());      //-----注释
		String retcode = "";
		String seqno = "";
		CarsListReturnEntity result = null;
		if (null != jsonObject) {
			result = com.alibaba.fastjson.JSONObject.parseObject(jsonObject.toString(),CarsListReturnEntity.class);
			try {
				if(result != null){
					retcode = result.getHead().getRetcode();
					seqno = result.getHead().getSeqno();
					if(dataHead.get("seqno").equals(seqno)){
						if("0".equals(retcode)){
							if(result.getBody().getDataList() != null){
								logger.info("GetCarsList username===" + result.getBody().getUsername());
								for(int i = 0; i < result.getBody().getDataList().size(); i++){
									logger.info("GetCarsList licenseno=" + result.getBody().getDataList().get(i).getLicenseno());
								}
							}else{
								result.getHead().setRetcode("1");
								result.getHead().setRetmsg("没有获取到车辆信息！");
								logger.info("没有获取到车辆信息！");
							}
						}
					}else{
						result.getHead().setRetcode("1");
						result.getHead().setRetmsg("请求与响应信息不一致！");
						logger.info("请求与响应信息不一致！");
					}
				}else{
					result = new CarsListReturnEntity();
					result.getHead().setRetcode("1");
					result.getHead().setRetmsg("车辆信息为空！");
					logger.info("车辆信息为空！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.getHead().setRetcode("1");
				result.getHead().setRetmsg("接口数据处理异常！");
			}
		}else{
			result = new CarsListReturnEntity();
			result.getHead().setRetcode("1");
			result.getHead().setRetmsg("车辆信息为空！");
			logger.info("车辆信息为空！");
		}
		return result;
	}
	
	/**
	 * 描述:获取用户用户手机号，接口方法GetUserPhone
	 * @param openid 唯一确定一个用户，是微信号的加密形式
	 * @return Map<String,String> 返回map retcode=0成功并带有身份证号，retcode=1失败并带有失败信息
	 * @author 戴元守 2017年9月21日 下午4:57:15
	 */
	public static Map<String,String> getUserPhoneFromPICC(String openid) {
		
		//升级正式注释掉下面url，下面的url是微信正式环境接口地址，由于测试环境注册信息有问题，所以用正式环境进行验证
		//String url = "http://10.133.12.20:7810/wx_interface/SMSPOpeningInterfaceService.aspx";            //-----------注释
		Map<String, String> returnmap = new HashMap<String, String>();
		
		Map<String,Map<String,String> >data = new HashMap<String, Map<String,String>>();
		Map<String ,String > dataHead = getRequestHeadPICC("GetUserPhone");
		//测试用
		//Map<String ,String > dataHead = getRequestHeadPICCTest("GetUserPhone");                 
		Map<String ,String > dataBody = new HashMap<String,String>();
		dataBody.put("openid", openid);
		data.put("head", dataHead);
		data.put("body", dataBody);
		JSONObject jsonObject = new JSONObject();
		jsonObject= JSONObject.fromObject(data);
		jsonObject = httpRequestPICC(requestUrl, "POST", jsonObject.toString());
		//测试用
		//jsonObject = httpRequestPICC(url, "POST", jsonObject.toString());
		String retcode = "";
		String retmsg = "";
		String seqno = "";
		if (null != jsonObject) {
			Map<String,Object> returnHead = (Map<String,Object>)jsonObject.get("head");
			Map<String,Object> returnBody = (Map<String,Object>)jsonObject.get("body");
			try {
				if(returnHead != null){
					retcode = returnHead.get("retcode").toString();
					retmsg = returnHead.get("retmsg").toString();
					seqno = returnHead.get("seqno").toString();
					returnmap.put("retcode", retcode);
					returnmap.put("retmsg", retmsg);
					if(dataHead.get("seqno").equals(seqno)){
						if("0".equals(retcode)){
							if(returnBody != null){
								returnmap.put("openid", returnBody.get("openid") == null ? "" : returnBody.get("openid").toString());
								returnmap.put("username", returnBody.get("username") == null ? "" : returnBody.get("username").toString());
								returnmap.put("identifyno", returnBody.get("identifyno") == null ? "" : returnBody.get("identifyno").toString());
								returnmap.put("identifytype", returnBody.get("identifytype") == null ? "" : returnBody.get("identifytype").toString());
								returnmap.put("phoneno", returnBody.get("phoneno") == null ? "" : returnBody.get("phoneno").toString());
								returnmap.put("realname", returnBody.get("realname") == null ? "" : returnBody.get("realname").toString());
							}else{
								returnmap.put("retcode", "1");
								returnmap.put("retmsg", "接口返回报文体信息为空！");
							}
						}
					}else{
						returnmap.put("retcode", "1");
						returnmap.put("retmsg", "接口返回报文信息与请求信息不是一对！");
					}
				}else{
					returnmap.put("retcode", "1");
					returnmap.put("retmsg", "接口返回报文头信息错误！");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnmap;
	}
	
	/**
	 * 描述:获取用户地理位置信息，接口方法GetUserLocation
	 * @param openid 唯一确定一个用户，是微信号的加密形式
	 * @return Map<String,String> 返回map retcode=0成功并带有身份证号，retcode=1失败并带有失败信息
	 * @author 戴元守 2017年9月21日 下午5:30:50
	 */
	public static Map<String,String> getUserLocationFromPICC(String openid) {
		
		//升级正式注释掉下面url，下面的url是微信正式环境接口地址，由于测试环境注册信息有问题，所以用正式环境进行验证
		//String url = "http://10.133.12.20:7810/wx_interface/SMSPOpeningInterfaceService.aspx";            //-----------注释
		Map<String, String> returnmap = new HashMap<String, String>();
		
		Map<String,Map<String,String> >data = new HashMap<String, Map<String,String>>();
		Map<String ,String > dataHead = getRequestHeadPICC("GetUserLocation");
		//测试用
		//Map<String ,String > dataHead = getRequestHeadPICCTest("GetUserLocation");                 
		Map<String ,String > dataBody = new HashMap<String,String>();
		dataBody.put("openid", openid);
		data.put("head", dataHead);
		data.put("body", dataBody);
		JSONObject jsonObject = new JSONObject();
		jsonObject= JSONObject.fromObject(data);
		jsonObject = httpRequestPICC(requestUrl, "POST", jsonObject.toString());
		//测试用
		//jsonObject = httpRequestPICC(url, "POST", jsonObject.toString());
		String retcode = "";
		String retmsg = "";
		String seqno = "";
		if (null != jsonObject) {
			Map<String,Object> returnHead = (Map<String,Object>)jsonObject.get("head");
			Map<String,Object> returnBody = (Map<String,Object>)jsonObject.get("body");
			try {
				if(returnHead != null){
					retcode = returnHead.get("retcode").toString();
					retmsg = returnHead.get("retmsg").toString();
					seqno = returnHead.get("seqno").toString();
					returnmap.put("retcode", retcode);
					returnmap.put("retmsg", retmsg);
					if(dataHead.get("seqno").equals(seqno)){
						if("0".equals(retcode)){
							if(returnBody != null){
								returnmap.put("openid", returnBody.get("openid") == null ? "" : returnBody.get("openid").toString());
								returnmap.put("longitude", returnBody.get("longitude") == null ? "" : returnBody.get("longitude").toString());
								returnmap.put("latitude", returnBody.get("latitude") == null ? "" : returnBody.get("latitude").toString());
								returnmap.put("precision", returnBody.get("precision") == null ? "" :returnBody.get("precision").toString());
								returnmap.put("address", returnBody.get("address") == null ? "" : returnBody.get("address").toString());
							}else{
								returnmap.put("retcode", "1");
								returnmap.put("retmsg", "接口返回报文体信息为空！");
							}
						}
					}else{
						returnmap.put("retcode", "1");
						returnmap.put("retmsg", "接口返回报文信息与请求信息不是一对！");
					}
				}else{
					returnmap.put("retcode", "1");
					returnmap.put("retmsg", "接口返回报文头信息错误！");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnmap;
	}
	
	/**
	 * 描述:调用总公司二次接口获取用户基本信息
	 * @param openid
	 * @return
	 * @author 吕亮 2017年10月30日 下午4:39:54
	 */
	public static Map<String,String> getUserInfoFromPICC(String openid) {
		
		//升级正式注释掉下面url，下面的url是微信正式环境接口地址，由于测试环境注册信息有问题，所以用正式环境进行验证
		//String url = "http://10.133.12.20:7810/wx_interface/SMSPOpeningInterfaceService.aspx";            //-----------注释
		Map<String, String> returnmap = new HashMap<String, String>();
		
		Map<String,Map<String,String> >data = new HashMap<String, Map<String,String>>();
		Map<String ,String > dataHead = getRequestHeadPICC("GetUserName");
		//测试用
		//Map<String ,String > dataHead = getRequestHeadPICCTest("GetUserInfo");                 
		Map<String ,String > dataBody = new HashMap<String,String>();
		dataBody.put("openid", openid);
		data.put("head", dataHead);
		data.put("body", dataBody);
		JSONObject jsonObject = new JSONObject();
		jsonObject= JSONObject.fromObject(data);
		jsonObject = httpRequestPICC(requestUrl, "POST", jsonObject.toString());
		//测试用
		//jsonObject = httpRequestPICC(url, "POST", jsonObject.toString());
		String retcode = "";
		String retmsg = "";
		String seqno = "";
		if (null != jsonObject) {
			Map<String,Object> returnHead = (Map<String,Object>)jsonObject.get("head");
			Map<String,Object> returnBody = (Map<String,Object>)jsonObject.get("body");
			try {
				if(returnHead != null){
					retcode = returnHead.get("retcode").toString();
					retmsg = returnHead.get("retmsg").toString();
					seqno = returnHead.get("seqno").toString();
					returnmap.put("retcode", retcode);
					returnmap.put("retmsg", retmsg);
					if(dataHead.get("seqno").equals(seqno)){
						if("0".equals(retcode)){
							if(returnBody != null){
								returnmap.put("openid", returnBody.get("openid") == null ? "" : returnBody.get("openid").toString());
								returnmap.put("head_img_in", returnBody.get("head_img_in") == null ? "" : returnBody.get("head_img_in").toString());
								returnmap.put("head_img_out", returnBody.get("head_img_out") == null ? "" : returnBody.get("head_img_out").toString());
								returnmap.put("nickname", returnBody.get("nickname") == null ? "" : returnBody.get("nickname").toString());
								returnmap.put("country", returnBody.get("country") == null ? "" : returnBody.get("country").toString());
								returnmap.put("province", returnBody.get("province") == null ? "" : returnBody.get("province").toString());
								returnmap.put("city", returnBody.get("city") == null ? "" : returnBody.get("city").toString());
								returnmap.put("sex", returnBody.get("sex") == null ? "" : returnBody.get("sex").toString());
								returnmap.put("crdt", returnBody.get("crdt") == null ? "" : returnBody.get("crdt").toString());
								returnmap.put("subscribe_flag", returnBody.get("subscribe_flag") == null ? "" : returnBody.get("subscribe_flag").toString());
							}else{
								returnmap.put("retcode", "1");
								returnmap.put("retmsg", "获取用户基本信息为空！");
							}
						}
					}else{
						returnmap.put("retcode", "1");
						returnmap.put("retmsg", "请求与响应信息不一致！");
					}
				}else{
					returnmap.put("retcode", "1");
					returnmap.put("retmsg", "请求头信息错误！");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnmap;
	}
	
	/**
	 * 描述:调用社交媒体服务平台二次接口实现人工客服接入
	 * @param openid
	 * @return 
	 * @author 吕亮 2017年11月2日 下午1:50:19
	 */
	public static Map<String,String> geGetTalkSessionFromPICC(String openid) {
		
		//升级正式注释掉下面url，下面的url是微信正式环境接口地址，由于测试环境注册信息有问题，所以用正式环境进行验证
		//String url = "http://10.133.12.20:7810/wx_interface/SMSPOpeningInterfaceService.aspx";            //-----------注释
		Map<String, String> returnmap = new HashMap<String, String>();
		Map<String,Object> returnHead = null;
		
		Map<String,Map<String,String> >data = new HashMap<String, Map<String,String>>();
		Map<String ,String > dataHead = getRequestHeadPICC("GetTalkSession");
		//测试用
		//Map<String ,String > dataHead = getRequestHeadPICCTest("GetTalkSession");                 
		Map<String ,String > dataBody = new HashMap<String,String>();
		dataBody.put("openid", openid);
		dataBody.put("content", "欢迎您选择\"北京人保财险\"人工客服，转接中~~");
		data.put("head", dataHead);
		data.put("body", dataBody);
		JSONObject jsonObject = new JSONObject();
		jsonObject= JSONObject.fromObject(data);
		jsonObject = httpRequestPICC(requestUrl, "POST", jsonObject.toString());
		//测试用
		//jsonObject = httpRequestPICC(url, "POST", jsonObject.toString());
		String retcode = "";
		String retmsg = "";
		String seqno = "";
		if (null != jsonObject) {
			returnHead = (Map<String,Object>)jsonObject.get("head");
			if(returnHead != null){
				retcode = returnHead.get("retcode").toString();
				retmsg = returnHead.get("retmsg").toString();
				seqno = returnHead.get("seqno").toString();
				if(dataHead.get("seqno").equals(seqno)){
					if("0".equals(retcode)){
						returnmap.put("retcode", retcode);
						returnmap.put("retmsg", retmsg);
					}else{
						returnmap.put("retcode", "1");
						returnmap.put("retmsg", "人工客服接入失败");
					}
				}else{
					returnmap.put("retcode", "1");
					returnmap.put("retmsg", "请求与响应信息不一致！");
				}
			}else{
				returnmap.put("retcode", "1");
				returnmap.put("retmsg", "人工客服接入失败");
			}
		}else{
			returnmap.put("retcode", "1");
			returnmap.put("retmsg", "人工客服接入失败");
		}
		return returnmap;
	}
	
	/**
	 * 描述:http请求
	 * @param url
	 * @param requestXML
	 * @param isproxy
	 * @return String
	 * @author 吕亮 2017年10月30日 上午10:38:04
	 */
	public static String sendHttpReqeust(String url, String requestXML, boolean isproxy, String encoding) {
		String msgXML = "";
		HttpURLConnection httpConnection;
		logger.info("微信sendHttpRequest请求报文:" + requestXML);
		try {
			// 使用代理
			if (isproxy) {
				boolean proxySet = Boolean.valueOf(SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxySet"));
				String proxyHost = SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxyHost");
				String proxyPort = SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxyPort");
				
				System.getProperties().put("proxySet",proxySet);
				System.getProperties().put("proxyHost",proxyHost);
				System.getProperties().put("proxyPort",proxyPort);
			}
			// 根据HttpURLConnection 以及传递的url建立连接
			httpConnection = (HttpURLConnection) new URL(url).openConnection();
			// 连接主机的超时时间（单位：毫秒）
			httpConnection.setConnectTimeout(TIME_OUT);
			// 从主机读取数据的超时时间（单位：毫秒）
			httpConnection.setReadTimeout(TIME_OUT);
			// 设定请求的方法为"POST"，默认是GET
			httpConnection.setRequestMethod("POST");
			// 设置是否从httpUrlConnection读入，默认情况下是true
			httpConnection.setDoOutput(true);
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			httpConnection.setDoInput(true);
			// 在允许用户交互（例如弹出一个验证对话框）的上下文中对此 URL 进行检查。
			httpConnection.setAllowUserInteraction(true);
			// 连接，从url.openConnection()至此的配置必须要在connect之前完成，
			httpConnection.connect();					
						
			// 2、发送数据
			// 现在通过输出流对象构建对象输出流对象，以实现输出可序列化的对象
			OutputStream outputStream = httpConnection.getOutputStream();
			// 向对象输出流写出数据，这些数据将存到内存缓冲区中
			outputStream.write(requestXML.getBytes(encoding));
			logger.debug("发送到服务端的报文:" + requestXML);
			outputStream.flush();
			outputStream.close();

			// 3、返回数据
			// 调用HttpURLConnection连接对象的getInputStream()函数,
			// 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
			InputStreamReader inputStreamReader = new InputStreamReader(
					httpConnection.getInputStream(),encoding);
			// 上边的httpConn.getInputStream()方法已调用,本次HTTP请求已结束,下边向对象输出流的输出已无意义，
			// 既使对象输出流没有调用close()方法，下边的操作也不会向对象输出流写入任何数据.
			// 因此，要重新发送数据时需要重新创建连接、重新设参数、重新创建流对象、重新写数据、
			// 重新发送数据
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			// 將返回的数据生成StringBuffer
			StringBuffer inputLines = new StringBuffer();
			char[] cbuf = new char[1024];
			for (int len = bufferedReader.read(cbuf); len > 0; len = bufferedReader
					.read(cbuf)) {
				inputLines.append(cbuf, 0, len);
			}
			// end 丢失换行符问题 add by duwei pncall 2011-6-13
			inputStreamReader.close();
			bufferedReader.close();

			// 4、关闭连接
			httpConnection.disconnect();
			msgXML = inputLines.toString();
			logger.info("微信sendHttpRequest返回客户端报文:" + msgXML);
		} catch (MalformedURLException e) {
			logger.error("HttpRequest错误信息:", e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("HttpRequest错误信息:", e);
			e.printStackTrace();
		} finally {
			if (isproxy) {
				System.getProperties().remove("proxySet");
				System.getProperties().remove("proxyHost");
				System.getProperties().remove("proxyPort");
			}
		}
		return msgXML;
	}
	/**
	 * 
			* 描述:
			* @param trCode  接口号
			* @return
			* @throws EpiccException
			* @author zhangjian2017年12月28日 上午10:26:36
	 */
	public static String getUrlFor95518(String trCode) throws EpiccException {
		
		//从字典表中获取,对应交易的url(value1:url)
		DicContentEntity dict = SysDicHelper.getInstance().getDicContentByDicTypeAndDicId("TRANS_ADDRESS", trCode);
		if(null==dict || null == dict.getIdValue()){
			throw new EpiccException("请检查"+trCode+"交易接口服务提供方接口版本号或发送者用户代码是否成功配置");
		}
		
		return dict.getIdValue();
	}
	
	public static String getQywxAuthRequestUrl(String redirectUrl,String state){
		//拼接微信授权跳转链接
		String wxPreffix="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+corpid+"&redirect_uri=";
		String wxSuffix="&response_type=code&scope=snsapi_base&agentid=" + agentid1 + "&state=" + state + "#wechat_redirect";
		try {
			String encryptUrl = URLEncoder.encode(redirectUrl,"utf-8");
			String url = wxPreffix+encryptUrl+wxSuffix;
			return url;
		} catch (Exception e) {
			throw new RuntimeException("企业微信授权链接转换失败");
		}
	}
	
	public static String getQywxAccessToken(){
		//先从redis里取，如果取不到，调用接口获取
		String requestUrl = "";
		JSONObject jsonObject = null;
		
		String errcode = "";
		String access_token = "";
		JedisPool redisPool = getJedisPool();
		Jedis jedis = redisPool.getResource();
		try {
			//取redis，没有或者过期，就重新取
			access_token = jedis.get(ConstantUtils.QYWX_ACCESS_TOKEN_REDIS_KEY);
			
			if(StringUtils.isBlank(access_token)){
				requestUrl = qywx_access_token_url.replace("ID", corpid).replace("SECRECT", corpsecret);
				jsonObject = httpRequest(requestUrl, "GET", null);
				// 如果请求成功
				if (null != jsonObject) {
					errcode = jsonObject.getString("errcode");
					access_token = jsonObject.getString("access_token");
					if(StringUtils.isNotBlank(errcode) && "0".equals(errcode) && StringUtils.isNotBlank(access_token)){
						//存放到redis中，过期时间为expires_in(7200s)
						jedis.set(ConstantUtils.QYWX_ACCESS_TOKEN_REDIS_KEY, access_token);
						jedis.expire(ConstantUtils.QYWX_ACCESS_TOKEN_REDIS_KEY, 3600);
					}
				}else{
					logger.info("获取企业微信获取token请求接口返回数据为空");
				}
			}
		} catch (Exception e) {
			// 获取token失败
			logger.error("获取企业微信token失败 errcode:"+ jsonObject.getInt("errcode")+" errmsg:"+ jsonObject.getString("errmsg"));
		}finally{
			//关闭redis
			if(jedis != null){
				jedis.close();
			}
		}
		return access_token;
	}
	
	public static Map<String,String> getQywxUserId(String code) {
		boolean proxySet = Boolean.valueOf(SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxySet"));
		String proxyHost = SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxyHost");
		String proxyPort = SysDicHelper.getInstance().getValueByDicTypeAndDicId("PROXY_CONFIG", "proxyPort");
		System.getProperties().put("proxySet", proxySet);//true:使用代理 false:不使用
		System.getProperties().put("proxyHost", proxyHost);//代理服务器IP
		System.getProperties().put("proxyPort", proxyPort);//代理服务器端口
		
		Map<String,String> resMap=new HashMap<String,String>();
		String weixinUrl = qywx_getuserinfo_url;
		weixinUrl = weixinUrl.replace("ACCESS_TOKEN", getQywxAccessToken());
		weixinUrl = weixinUrl.replace("CODE", code);
		String resData = httpsRequest(weixinUrl, "GET", null);
		System.out.println("获取企业微信用户信息返回报文：" + resData);
		
		com.alibaba.fastjson.JSONObject jsonObject=com.alibaba.fastjson.JSONObject.parseObject(resData);
		String errcode="";
		String errmsg="";
		String userid = "";
		String deviceid = "";
		String user_ticket = "";
		Integer expires_in = 0;
		//非企业成员唯一标识
		String openId = "";

		if(jsonObject != null){
			errcode = jsonObject.getString("errcode");
			errmsg = jsonObject.getString("errmsg");
			deviceid = jsonObject.getString("DeviceId");
			userid = jsonObject.getString("UserId");
			user_ticket = jsonObject.getString("user_ticket");
			expires_in = jsonObject.getInteger("expires_in");
			openId = jsonObject.getString("OpenId");
			if(StringUtils.isNotBlank(errcode) && "0".equals(errcode)){
				//企业成员可以取到的信息
				if(StringUtils.isNotBlank(userid)){
					resMap.put("errcode", errcode);
					resMap.put("errmsg", errmsg);
					resMap.put("UserId", userid);
					resMap.put("DeviceId", deviceid);
					resMap.put("user_ticket", user_ticket);
					resMap.put("expires_in", expires_in+"");
				}else{
					//非企业成员用户
					resMap.put("errcode", errcode);
					resMap.put("errmsg", errmsg);
					resMap.put("OpenId", openId);
					resMap.put("DeviceId", deviceid);
				}
			}else{
				//失败
				if(StringUtils.isBlank(errcode)){
					resMap.put("errcode", "100000001");
					resMap.put("errmsg", "没有获取到用户信息");
				}else{
					resMap.put("errcode", errcode);
					resMap.put("errmsg", errmsg);
				}
			}
		}else{
			resMap.put("errcode", "100000001");
			resMap.put("errmsg", "没有获取到用户信息");
		}
		System.getProperties().remove("proxySet");
		System.getProperties().remove("proxyHost");
		System.getProperties().remove("proxyPort");
		return resMap;
	}
	
}
