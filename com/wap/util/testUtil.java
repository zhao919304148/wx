package com.wap.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
public class testUtil {
	private static final String defaultCharset = "UTF-8";
	private static final String KEY_AES = "AES";
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
				content = data.getBytes(defaultCharset);
			}else{
				content = Base64.decodeBase64(data);
			}
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(defaultCharset),KEY_AES);
			Cipher cipher = Cipher.getInstance(KEY_AES);//创建密码器
			cipher.init(mode, keySpec);//初始化
			byte[] result = cipher.doFinal(content);
			if(encrypt){
				return Base64.encodeBase64String(result);
			}else{
				return new String(result,defaultCharset);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
 public static void main(String[] args) {
	 
	
	System.out.println(new testUtil().doEncrypt("赵硕", "34212223710a4561"));
	System.out.println(new testUtil().doDecrypt("PZvjxs+6r+xXuLam4xh7tQ==", "34212223710a4561"));
	/// System.out.println("PICC".getBytes(defaultCharset));
}   
}
