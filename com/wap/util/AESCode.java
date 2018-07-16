package com.wap.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jcajce.provider.digest.MD5.Digest;
import org.springframework.util.DigestUtils;

public class AESCode {
	private static final String UTF_8 = "utf-8";
	public static final  String  AESKEY = "openid";
	
	/**
	 * 提供密钥和向量进行加密
	 * 
	 * @param sSrc
	 * @param key
	 * @param iv
	 * @return
	 * @throws Exception
	 */
	private static String Encrypt(String sSrc, byte[] key, byte[] iv) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
		IvParameterSpec _iv = new IvParameterSpec(iv);// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, _iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes(UTF_8));
		return Base64.encodeBase64String(encrypted);
	}

	/**
	 * 提供密钥和向量进行解密
	 * 
	 * @param sSrc
	 * @param key
	 * @param iv
	 * @return
	 * @throws Exception
	 */
	private static String Decrypt(String sSrc, byte[] key, byte[] iv) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec _iv = new IvParameterSpec(iv);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, _iv);
		byte[] encrypted = Base64.decodeBase64(sSrc);
		byte[] original = cipher.doFinal(encrypted);
		return new String(original, UTF_8);
	}

	/**
	 * 使用密钥进行加密
	 * 
	 * @param sSrc
	 * @param keyStr
	 * @return
	 * @throws Exception
	 */
	public static String Encrypt(String sSrc, String keyStr) throws Exception {
		byte[] key = GeneralKey(keyStr);
		byte[] iv = GeneralIv(keyStr);
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
		IvParameterSpec _iv = new IvParameterSpec(iv);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, _iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes(UTF_8));
		return Base64.encodeBase64String(encrypted);
	}

	/**
	 * 使用密钥进行解密
	 * 
	 * @param sSrc
	 * @param keyStr
	 * @return
	 * @throws Exception
	 */
	public static String Decrypt(String sSrc, String keyStr) throws Exception {
		byte[] key = GeneralKey(keyStr);
		byte[] iv = GeneralIv(keyStr);
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec _iv = new IvParameterSpec(iv);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, _iv);
		byte[] encrypted = Base64.decodeBase64(sSrc);// 先用base64解码
		byte[] original = cipher.doFinal(encrypted);
		return new String(original, UTF_8);
	}

	/**
	 * 构建密钥字节码
	 * 
	 * @param keyStr
	 * @return
	 * @throws Exception
	 */
	private static byte[] GeneralKey(String keyStr) throws Exception {
		byte[] bytes = keyStr.getBytes(UTF_8);
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(bytes);
		return md.digest();
	}

	/**
	 * 构建加解密向量字节码
	 * 
	 * @param keyStr
	 * @return
	 * @throws Exception
	 */
	private static byte[] GeneralIv(String keyStr) throws Exception {
		byte[] bytes = keyStr.getBytes(UTF_8);
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(bytes);
		return md.digest();
	}
	/**
	 * 
			* 描述:随机生成
			* @param length
			* @return
			* @author qex 2017年1月24日 上午10:15:11
	 */
	public static String getRandomString(int length) { //length表示生成字符串的长度  
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
	    Random random = new SecureRandom();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }
	    return sb.toString();     
	}
	/***
	 * 
			* 描述:
			* AES加密报文信息:
			* 			【三位随机数字】+【16位随机iv】+【AES加密后报文】
			* @param src
			* 			原始报文
			* @param keyStr
			* 			加密的key
			* @param ivStr
			* 			iv向量
			* @return
			* @author 许宝众 2017年9月7日 下午3:42:41
	 * @throws Exception 
	 */
	public static String customEncrypt(String src,String keyStr) {
		String res = null;
		try{
			do{
				res = null;
				String ivStr = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
				byte[] key  = DigestUtils.md5Digest((keyStr+ivStr).getBytes(UTF_8));
				byte[] iv = ivStr.getBytes(UTF_8);
				String encrypt = Encrypt(src , key , iv);
				res = String.format("%3d", RandomUtil.getRandomNumber(1000))+ivStr+encrypt;
			}while(!StringEscapeUtils.escapeHtml(res).equals(res));
			return res;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
			* 描述:
			* 		解密customEncrypt的报文
			* @param src
			* 		加密后的报文
			* @param keyStr
			* 		解密key
			* @return
			* 		解密后的报文
			* @author 许宝众 2017年9月7日 下午3:53:10
	 * @throws Exception 
	 */
	public static String customDecypt(String src,String keyStr){
		if(StringUtils.isNotBlank(src)&&src.length()>19){
			try{
				String encrypt = src.substring(19);
				String ivStr = src.substring(3,19);
				byte[] iv = ivStr.getBytes(UTF_8);
				byte[] key  = DigestUtils.md5Digest((keyStr+ivStr).getBytes(UTF_8));
				return Decrypt(encrypt, key, iv);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/** 
	 * 字符串转换为16进制字符串 
	 *  
	 * @param s 
	 * @return 
	 */  
	public static String stringToHexString(String s) {  
	    String str = "";  
	    for (int i = 0; i < s.length(); i++) {  
	        int ch = (int) s.charAt(i);  
	        String s4 = Integer.toHexString(ch);  
	        str = str + s4;  
	    }  
	    return str;  
	}  

	/** 
	 * 16进制字符串转换为字符串 
	 *  
	 * @param s 
	 * @return 
	 */  
	public static String hexStringToString(String s) {  
	    if (s == null || s.equals("")) {  
	        return null;  
	    }  
	    s = s.replace(" ", "");  
	    byte[] baKeyword = new byte[s.length() / 2];  
	    for (int i = 0; i < baKeyword.length; i++) {  
	        try {  
	            baKeyword[i] = (byte) (0xff & Integer.parseInt(  
	                    s.substring(i * 2, i * 2 + 2), 16));  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    try {  
	        s = new String(baKeyword, "gbk");  
	        new String();  
	    } catch (Exception e1) {  
	        e1.printStackTrace();  
	    }  
	    return s;  
	}  
	public static void main(String[] args) throws Exception {
//		String encrypt = Encrypt("123", "12356456");
//		System.out.println(encrypt);
//		String da = "{\"amount\":\"123456\",\"userId\":\"123456\",\"requestTime\":\"2017-01-22 11:24:42\",\"openId\":\"123456\",\"orderList\":[{\"orderNo\":\"123456\",\"goodName\":\"123456\",\"count\":\"123456\",\"price\":\"123456\",\"score\":\"123456\",\"cash\":\"123456\",\"dateTime\":\"2017-01-22 11:24:42\"},{\"orderNo\":\"123457\",\"goodName\":\"123456\",\"count\":\"123456\",\"price\":\"123456\",\"score\":\"123456\",\"cash\":\"123456\",\"dateTime\":\"2017-01-22 11:24:42\"}]}";
//		try {
//			String decrypt = Decrypt("191H+EH+6Ul9ynuRzWzG5+Sfgy+YYt67DoRyULqjwBU=",AESKEY);
////			String decrypt = Decrypt("oocZPK9pp58keVMle+1ofZg63FmhVtRb80mCJQEKF9Q=","yB/ChRXi6s7Pz1ZhP9yOvQ==");
//			String jiami = Encrypt("osX-mwGsVPC5wTYU1eYB7Dxcvmrg",AESKEY);
////			String jiami = Encrypt(da,"yB/ChRXi6s7Pz1ZhP9yOvQ==");
//			System.out.println("加密密文："+jiami);
//			System.out.println(decrypt);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		String keyStr = "WXTEMPLATEMESSAGE";
		String sSrc = "osX-mwHc1xa7SZEjYG4fIPyFUSCo";
		String a="20393962316238316630666466666434373864412b354237524a70487a54712b46325a4b435559527175314e6179527a72337369313847302f62356370453dda";
		String encrypt = hexStringToString(a);
		System.out.println(encrypt);
	//	String encryptURLEncoder = URLEncoder.encode(URLEncoder.encode(URLEncoder.encode(encrypt,"utf-8"),"utf-8"),"utf-8");
//		System.out.println("encrypt:"+encrypt);
		System.out.println("encrypt:"+customDecypt(encrypt,keyStr));
		
		
	//	System.out.println(customDecypt(URLDecoder.decode(URLDecoder.decode(URLDecoder.decode(encryptURLEncoder,"utf-8"),"utf-8"),"utf-8"),keyStr));
		
		
	}
	
}
