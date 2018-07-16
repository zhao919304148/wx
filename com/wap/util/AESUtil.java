package com.wap.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * @ClassName: AESUtil 
 * @Description: TODO(AES加解密工具类) 
 * @author: shaomd  
 * @date 2016年12月9日 下午4:21:07
 */
public class AESUtil {
	
	private static final String KEY_ALGORITHM = "AES";
	
	private static final String CHARSET = "UTF-8";
	private static final String KEY_MD5 = "MD5";
	private static MessageDigest md5Digest;
	
	static {
		try {
			md5Digest = MessageDigest.getInstance(KEY_MD5);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @Title: initKey 
	 * @Description: TODO(初始化密钥) 
	 * @author: shaomd 
	 */
	public static String initAESKey() throws NoSuchAlgorithmException{
		KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
		kgen.init(128);
		SecretKey key = kgen.generateKey();
		return Base64.encodeBase64String(key.getEncoded());
	}
	
	/**
	 * AES加密
	 * 
	 * @param content 待加密的内容
	 * @param encryptKey 加密密钥
	 * @return 加密后的byte[]
	 * @throws Exception
	 */
	public static String aesEncryptToBytes(String data, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(key),KEY_ALGORITHM));
		return Base64.encodeBase64String(cipher.doFinal(data.getBytes(CHARSET)));
	}
	
	/**
	 * AES解密
	 * 
	 * @param encryptBytes 待解密的byte[]
	 * @param decryptKey 解密密钥
	 * @return 解密后的String
	 * @throws Exception
	 */
	public static String aesDecryptByBytes(String data, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(key), "AES"));
		byte[] decryptBytes = cipher.doFinal(Base64.decodeBase64(data));
		return new String(decryptBytes,CHARSET);
	}
	/**
     * 加密
     * @param data 加密内容
     * @param key 加密密钥
     * @return
     */
    public static String doEncrypt(String data,String key){
    	return doAES(data,key,Cipher.ENCRYPT_MODE);
    }
    /**
     * 解密
     * @param data 解密内容
     * @param key 解密密钥
     * @return
     */
    public static String doDecrypt(String data,String key){
    	return doAES(data,key,Cipher.DECRYPT_MODE);
    }
    /**
     * 加解密
     * @param data
     * @param key
     * @param decryptMode
     * @return
     */
	private static String doAES(String data, String key, int mode) {
		
		try{
			if(StringUtils.isBlank(data)||StringUtils.isBlank(key)){
				return null;
			}
			boolean encrypt = mode == Cipher.ENCRYPT_MODE;
			byte[] content;
			if(encrypt){
				content = data.getBytes(CHARSET);
			}else{
				content = Base64.decodeBase64(data);
			}
			SecretKeySpec keySpec = new SecretKeySpec(md5Digest.digest(key.getBytes(CHARSET)),KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//创建密码器
			cipher.init(mode, keySpec);//初始化
			byte[] result = cipher.doFinal(content);
			if(encrypt){
				return Base64.encodeBase64String(result);
			}else{
				return new String(result,CHARSET);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		String content = "responseId=赵&resCode=1222&resMsg=2221&messgae=1112";
		System.out.println("加密前：" + content);
		String key ="17212223710f4560";
		String aesEncryptToBytes = aesEncryptToBytes(content,  Base64.encodeBase64String(key.getBytes()));
		System.out.println("加密后："+ new String(Base64.decodeBase64(aesEncryptToBytes.getBytes()),"UTF-8"));
//		String decrypt = aesDecryptByBytes(aesEncryptToBytes,  Base64.encodeBase64String("17212223710f4560".getBytes()));
//		System.out.println("解密后：" + decrypt);
	}
}