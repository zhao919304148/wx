package com.wap.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.util.encoders.UrlBase64;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;

/**
 * 
		
* 描述:通用工具类
*
* @author 许宝众
* @version 1.0
* @since 2017年5月15日 上午11:02:11
 */
public class CommonUtils {
	/***
	 * 随机字符数组（包括A-Za-z0-9）
	 */
	public static final char[] RANDOM_NUMBER_LETTER = new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	/**默认字符编码UTF-8**/
	public static final String DEFAULT_ENCODING = "UTF-8";
	private static final Log logger = LogFactory.getLog(CommonUtils.class);
	/**
	 * 加密手机号，将手机号中间位置替换为*
	 * @param phoneNumber
	 * @return
	 */
	public static String shieldPhoneNumber(String phoneNumber){
		if(org.springframework.util.StringUtils.hasText(phoneNumber)){
			phoneNumber=phoneNumber.trim();
			int length = phoneNumber.length();
			if(length==11){
				phoneNumber=phoneNumber.substring(0,3)+"****"+phoneNumber.substring(7);
			}else if (length>4){
				String preffix="";
				for (int i = 0; i < length-4; i++) {
					preffix+="*";
				}
				phoneNumber=preffix+phoneNumber.substring(length-4,length);
			}else{
				String preffix="";
				for (int i = 0; i < length; i++) {
					preffix+="*";
				}
				phoneNumber=preffix;
			}
			return phoneNumber;
		}else{
			return "";
		}
	}
	
	/**
	 * 描述:验证用户是否从微信浏览器打开页面
	 * @param request
	 * @param openId
	 * @return
	 * @author 吕亮 2017年10月27日 下午6:41:47
	 */
	public static JSONObject checkWxOpen(HttpServletRequest request, String openId ){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("resCode","0");//默认成功返回，错误时覆盖此值
		String userAgent = request.getHeader("User-Agent");
		if(userAgent.indexOf("MicroMessenger") == -1){
			logger.info("userAgent not have MicroMessenger!");
			jsonObject.put("resCode","1");
			jsonObject.put("msg","请在微信客户端打开链接！");
			return jsonObject;
		}
		if("".equals(openId)){
			jsonObject.put("resCode","1");
			jsonObject.put("msg", "请从微信公众号菜单进入！");
			return jsonObject;
		}
		return jsonObject;
	}
	
	/**
	 * 描述:字符串剔除空格
	 * @param str
	 * @return
	 * @author 吕亮2017年10月27日 下午8:08:38
	 */
	public static String trim(String str) {
		String newstr = "";
		if (str != null && !"".equals(str)) {
			newstr = str.trim();
		}
		return newstr;
	}
	/**
	 * null转为""
	 * @return
	 */
	public static String parseNull2String(String src){
		return StringUtils.isBlank(src)?"":src;
	}
	/**
	 * 
	 * 描述:
	 * 自定义算法加密较短文本，使用UrlBase64+分块填入随机字符<br>
	 * 具体规则：<br>
	 * <li>第一步：UrlBase64将原始文本加密</li>
	 * <li>第二步：加密后文本分割，并在其中插入一个随机数字或者字母，形成一个伪Base64加密报文</li>
	 * @param text
	 * 			待加密文本
	 * @param blockSize
	 * 			块大小(分割的字符长度大小)
	 * @param minSplits
	 * 			最小分割次数（根据指定的块大小分割的块数必须不小于此参数）
	 * @author 许宝众2017年12月15日 下午1:16:09
	 * @throws UnsupportedEncodingException 
	 */
	public static String encodeShortText(String text,int blockSize,int minSplits){
		String res = null;
		try{
			String step1 = new String(UrlBase64.encode(text.getBytes(DEFAULT_ENCODING)),DEFAULT_ENCODING);
			int splits = step1.length()/blockSize;
			if(step1.length()%blockSize!=0){
				splits++;
			}
			Assert.isTrue(splits>=minSplits,"UrlBase64加密后字符：【"+step1+"】，分割【"+minSplits+"】块，必须保证每块长度大于"+blockSize);
			char[] step1Arr = step1.toCharArray();
			char[] step2Arr = new char[step1Arr.length+splits-1];
			int index = 0;
			for (int i = 0; i < step1Arr.length; i++) {
				if(i%blockSize==0&&i!=0){
					step2Arr[index++] = RANDOM_NUMBER_LETTER[(int) (RANDOM_NUMBER_LETTER.length*Math.random())];
				}
				step2Arr[index++] = step1Arr[i];
			}
			res = new String(step2Arr);
		}catch(UnsupportedEncodingException ex){
			logger.info("不支持的字符编码类型：",ex);
			throw new RuntimeException(ex);
		}
		return res;
	}
	
	/**
	 * 
	 * 描述:
	 * 自定义算法解密较短文本
	 * @param text
	 * 			加密后报文
	 * @param blockSize
	 * 			分块大小
	 * @return
	 * @author 许宝众2017年12月15日 下午1:28:08
	 * @throws UnsupportedEncodingException 
	 */
	public static String decodeShortText(String text,int blockSize){
		String res = null;
		try{
			char[] charArray = text.toCharArray();
			int step = blockSize+1;
			char[] newCharArray = new char[charArray.length-charArray.length/step];
			int index = 0;
			int abortIndex = blockSize;
			for (int i = 0; i < charArray.length; i++) {
				if(i!=abortIndex){
					newCharArray[index++] = charArray[i];
				}else{
					abortIndex = abortIndex+step;
				}
			}
			res = new String(UrlBase64.decode(new String(newCharArray).getBytes(DEFAULT_ENCODING)),DEFAULT_ENCODING);
		}catch(UnsupportedEncodingException ex){
			logger.info("不支持的字符编码类型：",ex);
			throw new RuntimeException(ex);
		}
		return res;
	}
	
	/**
	 * 公钥验签
	 * @param key
	 * @param content
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 * @throws SignatureException 
	 * @throws InvalidKeyException 
	 */
	public static boolean verifyPublicKeySign(PublicKey publicKey,String content,String sign) throws NoSuchAlgorithmException, SignatureException, UnsupportedEncodingException, InvalidKeyException{
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initVerify(publicKey);
		signature.update(content.getBytes(DEFAULT_ENCODING));
		byte[] decode = UrlBase64.decode(sign);
		return signature.verify(decode);
	}
	/**
	 * 
			* 描述:
			* 私钥签名
			* @param privateKey
			* @param content
			* @return
			* @throws SignatureException
			* @throws UnsupportedEncodingException
			* @throws NoSuchAlgorithmException
			* @throws InvalidKeyException
			* @author 许宝众 2017年12月18日 上午11:20:20
	 */
	public static String generatePrivateKeySign(PrivateKey privateKey,String content) throws SignatureException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException{
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initSign(privateKey);
		signature.update(content.getBytes(DEFAULT_ENCODING));
		return new String(UrlBase64.encode(signature.sign()),DEFAULT_ENCODING);
	}
	
	/**
	 * 
			* 描述:
			* 公钥加密
			* @param publicKey
			* @param content
			* @return
			* @throws UnsupportedEncodingException
			* @throws IllegalBlockSizeException
			* @throws BadPaddingException
			* @throws NoSuchAlgorithmException
			* @throws NoSuchPaddingException
			* @throws InvalidKeyException
			* @author 许宝众2017年12月18日 下午3:24:55
	 */
	public static String encryptMessageWhithPublicKey(PublicKey publicKey,String content) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException{
		//公钥加密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		// 加密时超过117字节就报错。为此采用分段加密的办法来加密   
		byte[] data = content.getBytes(DEFAULT_ENCODING);
		byte[] rsaData = null;
		int dataLength = data.length;
		for (int i = 0; i < dataLength; i=i + 64) {    
			// 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码  
			byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + 64));    
			rsaData = ArrayUtils.addAll(rsaData, doFinal);
		}  
		return  new String(UrlBase64.encode(rsaData),DEFAULT_ENCODING);
	}
	
	/**
	 * 
			* 描述:
			* 私钥解密
			* @param privateKey
			* @param encrypt
			* 			加密后报文
			* @return
			* @author 许宝众 2017年12月18日 上午11:23:04
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws UnsupportedEncodingException 
	 * @throws InvalidKeyException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static String decryptMessageWithPrivateKey(PrivateKey privateKey,String encrypt) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException{
		//私钥解密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] data = UrlBase64.decode(encrypt.getBytes(DEFAULT_ENCODING));
		// 解密时超过128字节就报错。为此采用分段解密的办法来解密  
		byte[] decryptRes = null;
		for (int i = 0; i < data.length; i = i + 128) {  
		    byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + 128));  
		    decryptRes = ArrayUtils.addAll(decryptRes, doFinal);
		}  
		return new String(decryptRes,DEFAULT_ENCODING);
	}
	
	/**
	 * 
			* 描述:
			* 自定义openId加密算法
			* @param openId
			* @return
			* @author 许宝众2017年12月18日 下午1:40:41
	 */
	public static String encodeOpenId(String openId){
		return encodeShortText(openId, 4, 3);
	}
	
	/**
	 * 
			* 描述:
			* 自定义openId解密算法
			* @param openId
			* @return
			* @author 许宝众2017年12月18日 下午1:40:41
	 */
	public static String decodeOpenId(String openId){
		return decodeShortText(openId, 4);
	}
	
	/**
	 * 
			* 描述:
			* 自定义cardNo加密算法
			* @param openId
			* @return
			* @author 许宝众2017年12月18日 下午1:40:41
	 */
	public static String encodeCardNo(String cardNo){
		return encodeShortText(cardNo, 2, 4);
	}
	
	/**
	 * 
			* 描述:
			* 自定义cardNo解密算法
			* @param openId
			* @return
			* @author 许宝众2017年12月18日 下午1:40:41
	 */
	public static String decodeCardNo(String cardNo){
		return decodeShortText(cardNo, 2);
	}
	/**
	 * 读取cer证书获得公钥
	 * @throws CertificateException 
	 */
	public static PublicKey getPublicKeyByCer(InputStream in) throws CertificateException{
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(in);
		PublicKey pk = cert.getPublicKey();
		return pk;
	}
	/**
	 *  读取cer证书获得公钥
	 * @param filePath
	 * 			文件全路径
	 * @return
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 */
	public static PublicKey getPublicKeyByCer(String filePath) throws CertificateException, FileNotFoundException{
		FileInputStream in = new FileInputStream(new File(filePath));
		return getPublicKeyByCer(in);
	}
	/**
	 * 得到私钥
	 * @param in
	 * 			keystore文件流
	 * @param password
	 * 			keystore密码
	 * @param keyAlias
	 * 			key别名
	 * @param keyPwd
	 * 			key password
	 * @return
	 * @throws KeyStoreException 
	 * @throws NoSuchAlgorithmException 
	 * @throws UnrecoverableKeyException 
	 * @throws IOException 
	 * @throws CertificateException 
	 */
	public static PrivateKey getPrivateKeyByKeyStore(InputStream in,String password,String keyAlias,String keyPwd) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException, IOException{
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(in , password.toCharArray());
		PrivateKey privateKey = (PrivateKey) ks.getKey(keyAlias, keyPwd.toCharArray());
		return privateKey;
	}
	/**
	 *  得到私钥
	 * @param filePath
	 * @param password
	 * @param keyAlias
	 * @param keyPwd
	 * @return
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static PrivateKey getPrivateKeyByKeyStore(String filePath,String password,String keyAlias,String keyPwd) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException, IOException{
		InputStream in = new FileInputStream(new File(filePath));
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(in , password.toCharArray());
		PrivateKey privateKey = (PrivateKey) ks.getKey(keyAlias, keyPwd.toCharArray());
		return privateKey;
	}
	
	/**
	 * 
	 * 描述:
	 * 使用私钥进行数据加密
	 * @param content
	 * @param privateKey
	 * @return
	 * @author 许宝众2018年1月29日 下午1:56:00
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws UnsupportedEncodingException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	public static String encryptMessageWithPrivateKey(String content,PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		// 加密时超过117字节就报错。为此采用分段加密的办法来加密   
		byte[] data = content.getBytes(DEFAULT_ENCODING);
		byte[] rsaData = null;
		int dataLength = data.length;
		for (int i = 0; i < dataLength; i=i + 64) {    
			// 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码  
			byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + 64));    
			rsaData = ArrayUtils.addAll(rsaData, doFinal);
		}  
		return  new String(UrlBase64.encode(rsaData),DEFAULT_ENCODING);
		
	}
	/**
	 * 
	 * 描述:
	 * @param publicKey
	 * @param encrypt
	 * @return
	 * @author 许宝众 2018年1月29日 下午2:01:12
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws UnsupportedEncodingException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	public static String decryptMessageWithPublicKey(PublicKey publicKey,String encrypt) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException{
		//私钥解密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] data = UrlBase64.decode(encrypt.getBytes(DEFAULT_ENCODING));
		// 解密时超过128字节就报错。为此采用分段解密的办法来解密  
		byte[] decryptRes = null;
		for (int i = 0; i < data.length; i = i + 128) {  
		    byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + 128));  
		    decryptRes = ArrayUtils.addAll(decryptRes, doFinal);
		}  
		return new String(decryptRes,DEFAULT_ENCODING);
	}
	
	
	/**
	 * 对一组参数进行签名<br>
	 * 签名规则：<br>
	 * 参数Map中参数根据key进行字典排序，组织参数字符串：md5Key=val&key1=val1&key2=val2&key3=val3...<br>
	 * 对签名字符串进行MD5+Base64运算生成签名字符串
	 * @return
	 */
	public static String generateSignForParams(String md5Key,Map<String,String> params){
		ArrayList<String> keyList = new ArrayList<String>();
		for (String key : params.keySet()) {
			keyList.add(key);
		}
		java.util.Collections.sort(keyList);
		StringBuffer signFormatSb = new StringBuffer();
		for (String key : keyList) {
			signFormatSb.append("&"+key+"="+parseNull2String(params.get(key)));
		}
		signFormatSb.insert(0,"md5Key="+md5Key);
		try {
			return new String(org.bouncycastle.util.encoders.Base64.encode(DigestUtils.md5(signFormatSb.toString().getBytes(DEFAULT_ENCODING))),DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 验证签名
	 * @param md5Key
	 * @param params
	 * @param sign
	 * @return
	 */
	public static boolean verifyParamsSign(String md5Key,Map<String,String> params,String sign){
		return generateSignForParams(md5Key, params).equals(sign);
	}
	
	public static String generateMD5Key(){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			sb.append(UUID.randomUUID().toString().replace("-", ""));
		}
		return sb.toString();
	}
	
	
	public static void main(String[] args) throws Exception {
		
		
		System.out.println(CommonUtils.decodeCardNo("RFGpBIQ0kJZ2MT6dE3MD4Q5zNjhM."));
/*		String content = "012345678许宝众012345678许宝众012345678许宝众012345678许宝众012345678许宝众012345678许宝众012345678许宝众012345678许宝众012345678许宝众012345678许宝众";
//		String encrpyt = encodeCardNo(content);
//		String decrypt = decodeCardNo(encrpyt);
//		System.out.println(new String(UrlBase64.decode(content.getBytes(DEFAULT_ENCODING)),DEFAULT_ENCODING));
//		System.out.println("content:"+content);
//		System.out.println("encrpyt:"+encrpyt);
//		System.out.println("decrypt:"+decrypt);
		KeyStore ks = KeyStore.getInstance("JKS");
		InputStream in = CommonUtils.class.getResourceAsStream("/interface.keystore");
		ks.load(in , "piccbj1100".toCharArray());
		PrivateKey privateKey = (PrivateKey) ks.getKey("maintain_10000250", "piccbj@password".toCharArray());
		String encrypt = encryptMessageWithPrivateKey(content, privateKey);
		System.out.println("encrypt:"+encrypt);
//		String content = "012345许小宝是个大好人";
//		String sign = generatePrivateKeySign(privateKey,content);
//		System.out.println("sign:\n"+sign);
		PublicKey publicKey = null;
		String cerFile = "D:\\keys\\bjwxplat\\dev\\maintain_10000250_dev.cer";
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		InputStream inStream = new FileInputStream(new File(cerFile));
		X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream );
		publicKey = cert.getPublicKey();
		System.out.println("deccrypt:\n"+decryptMessageWithPublicKey(publicKey, encrypt));
//		System.out.println("verify:"+verifyPublicKeySign(publicKey , content, sign));
*/	
	System.out.println(generateMD5Key());	
	}
/*	@Test
	public void testPrivateGenerateSign() throws Exception{
		String comcode = "maintain_"+"10000534";
		KeyStore ks = KeyStore.getInstance("JKS");
		InputStream keyStoreIn = WxController.class.getResourceAsStream("/interface.keystore");
		ks.load(keyStoreIn , "piccbj1100".toCharArray());
		PrivateKey privateKey = (PrivateKey) ks.getKey(comcode, "piccbj@password".toCharArray());
		String content = "responseId=736fbd35-5d97-4fec-bcaf-1d383c62de8c&resCode=1&resMsg=成功&message={\"cardId\":\"QNBY0000000007\",\"cardType\":\"1066\",\"cardTypeName\":\"全年保养券\",\"orderNo\":\"10000000000012\"}";
		String sign = generatePrivateKeySign(privateKey, content );
		System.out.println("content:\n"+content);
		System.out.println("sign:\n"+sign);
		
		PublicKey publicKey = CommonUtils.getPublicKeyByCer("D:\\keys\\bjwxplat\\dev\\maintain_10000534_dev.cer");
		System.out.println("verify:\n"+CommonUtils.verifyPublicKeySign(publicKey, content, sign));
		
	}
	@Test
	public void testSign() throws Exception{
//		String res = "{\"sign\":\"NWjk8v7g3G7temDTTUk120aObmC/pZkMvuOcVAgG2Dl1PUYLQtOa7BR8FKhQ7U1dbV4bWg8jEj1Y\r\n3TIXVQlP2wLVozp8u4MFGy2ljw7OoagVSOC3Y5wAKgOscH0vOipQrvgip05AGz7UwV4w6mQ8Okw8\r\nyppiF/2avpIu39k3Feo=\",\"message\":{\"cardId\":\"QNBY0000000007\",\"cardType\":\"1066\",\"cardTypeName\":\"全年保养券\",\"orderNo\":\"10000000000012\"},\"responseId\":\"736fbd35-5d97-4fec-bcaf-1d383c62de8c\",\"resCode\":\"1\",\"resMsg\":\"成功\"}";
//		System.out.println("res:\n"+res);
//		JSONObject resJson = JSON.parseObject(res);
//		String resSign = null;
//		String preSign = null;
//		//生成签名
//		String resSignFormat = "responseId=%s&resCode=%s&resMsg=%s&messgae=%s";
//		preSign = String.format(resSignFormat, 
//		CommonUtils.parseNull2String(resJson.getString("responseId")),
//		CommonUtils.parseNull2String(resJson.getString("resCode")),
//		CommonUtils.parseNull2String(resJson.getString("resMsg")),
//		CommonUtils.parseNull2String(resJson.getString("message")));
//		String comcode = "maintain_"+"10000534";
//		KeyStore ks = KeyStore.getInstance("JKS");
//		InputStream keyStoreIn = WxController.class.getResourceAsStream("/interface.keystore");
//		ks.load(keyStoreIn , "piccbj1100".toCharArray());
//		PrivateKey privateKey = (PrivateKey) ks.getKey(comcode, "piccbj@password".toCharArray());
//		resSign = CommonUtils.generatePrivateKeySign(privateKey,preSign);
//		System.out.println("preSign:"+preSign);
//		System.out.println("resSign:"+resSign);
	}
	@Test
	public void testCancelOrder() throws Exception{
		String orderId = "10000000000001";
		String requestId = UUID.randomUUID().toString();
		requestId = "b32ac605-b230-4819-ac81-fd105d9dff4f";
		String signFormat = "requestId=%s&orderId=%s";
		String preSign = String.format(signFormat, requestId,orderId);
		String comcode = "maintain_"+"10000534";
		KeyStore ks = KeyStore.getInstance("JKS");
		InputStream keyStoreIn = WxController.class.getResourceAsStream("/interface.keystore");
		ks.load(keyStoreIn , "piccbj1100".toCharArray());
		PrivateKey privateKey = (PrivateKey) ks.getKey(comcode, "piccbj@password".toCharArray());
		String sign = CommonUtils.generatePrivateKeySign(privateKey,preSign);
		System.out.println("orderId:"+orderId);
		System.out.println("requestId:"+requestId);
		System.out.println("sign:"+sign);
	}*/
	
}
